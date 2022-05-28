package cn.arros.server.service;

import cn.arros.server.constant.BuildStatus;
import cn.arros.server.constant.ConfigType;
import cn.arros.server.entity.BuildHistory;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.entity.Node;
import cn.arros.server.mapper.NodeMapper;
import cn.arros.server.properties.ArrosProperties;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.apache.sshd.sftp.client.fs.SftpFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @Author Verge
 * @Date 2021/11/6 16:17
 * @Version 1.0
 * ssh连接远程主机执行脚本的环境变量问题 https://feihu.me/blog/2014/env-problem-when-ssh-executing-command-on-remote/
 * Get the PID of a process started with nohup via ssh https://stackoverflow.com/questions/12647382/get-the-pid-of-a-process-started-with-nohup-via-ssh
 * ssh远程执行nohup命令不退出 https://www.shangmayuan.com/a/322e78ab26014e5db8bae511.html
 */
public class DeployService implements Runnable{
    /*
    1. 建立连接
    2. 传输文件
    3. 执行命令
    4. 关闭连接
     */

    private final BuildInfo buildInfo;
    private final BuildHistory buildHistory;
    private SshClient sshClient;
    private ClientSession session;
    private SftpFileSystem fs;
    private Path targetPath;
    private final BuildHistoryService buildHistoryService = SpringUtil.getBean(BuildHistoryService.class);

    public DeployService(BuildInfo buildInfo, BuildHistory buildHistory) {
        this.buildInfo = buildInfo;
        this.buildHistory = buildHistory;
    }

    @Override
    public void run() {
        List<Supplier<Boolean>> list = new ArrayList<>(4);
        list.add(this::connect);
        list.add(this::transFile);
        list.add(this::execCommand);
        list.add(this::closeConnect);

        for (Supplier<Boolean> booleanSupplier : list) {
            if (!booleanSupplier.get()) {
                buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.DEPLOY_FAILED);
                break;
            }
        }
        buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.DEPLOY_COMPLETED);
    }

    // TODO: 使用公钥连接
    private boolean connect() {
        NodeMapper nodeMapper = SpringUtil.getBean(NodeMapper.class);
        Node node = nodeMapper.selectById(buildInfo.getNodeId());

        sshClient = SshClient.setUpDefaultClient();
        sshClient.start();

        String username = node.getUsername();
        String host = node.getHost();
        int port = node.getPort();

        try {
            session = sshClient.connect(username, host, port)
                    .verify(2, TimeUnit.MINUTES)
                    .getClientSession();

            SymmetricCrypto symmetricCrypto = SpringUtil.getBean(SymmetricCrypto.class);

            session.addPasswordIdentity(symmetricCrypto.decryptStr(node.getPassword()));
            session.auth().verify(2, TimeUnit.MINUTES);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean transFile() {
        ArrosProperties arrosProperties = SpringUtil.getBean(ArrosProperties.class);
        try {
            fs = SftpClientFactory.instance().createSftpFileSystem(session);
            String buildPath = arrosProperties.getConfig(ConfigType.BUILD_PATH);
            Path remotePath = fs.getDefaultDir()
                    .resolve(buildPath)
                    .resolve(buildInfo.getId());
            if (!Files.exists(remotePath)) Files.createDirectories(remotePath);
            targetPath = remotePath.resolve("app.jar");

            File file = new File(buildHistory.getResultName());
            InputStream inputStream = new FileInputStream(file);
            Files.deleteIfExists(targetPath);
            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean execCommand() {
        try {
            String res = session.executeRemoteCommand("nohup java -jar " + targetPath + " >/dev/null 2>&1 & echo $!");
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean closeConnect() {
        try {
            fs.close();
            session.close();
            sshClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }




}
