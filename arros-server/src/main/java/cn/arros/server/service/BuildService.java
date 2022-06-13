package cn.arros.server.service;

import cn.arros.server.component.MavenInvokerBuilder;
import cn.arros.server.constant.BuildStatus;
import cn.arros.server.constant.ConfigType;
import cn.arros.server.constant.DeployType;
import cn.arros.server.entity.BuildHistory;
import cn.arros.server.entity.BuildInfo;
import cn.arros.server.properties.ArrosProperties;
import cn.arros.server.utils.GitUtils;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @Author Verge
 * @Date 2021/11/2 10:20
 * @Version 1.0
 */
public class BuildService implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(BuildService.class);

    private final BuildInfo buildInfo;

    private final BuildHistory buildHistory = new BuildHistory();

    private final String buildHistoryId = MDC.get("buildHistoryId");

    private final BuildHistoryService buildHistoryService = SpringUtil.getBean(BuildHistoryService.class);

    private final MavenInvokerBuilder mavenInvokerBuilder = SpringUtil.getBean(MavenInvokerBuilder.class);

    public BuildService(BuildInfo buildInfo) {
        this.buildInfo = buildInfo;
    }
    /*
     * 构建流程
     * 准备：向表build_history插入基本信息
     * 拉取资源：执行git pull获取最新源码
     * 执行命令：执行打包命令
     * 部署：部署至服务器
     */
    @Override
    public void run() {
        List<Supplier<Boolean>> list = new ArrayList<>(4);
        list.add(this::prepare);
        list.add(this::updateSource);
        list.add(this::build);
        list.add(this::deploy);

        for (Supplier<Boolean> booleanSupplier : list) {
            if (!booleanSupplier.get()) {
                buildHistoryService.updateBuildStatus(buildHistoryId, BuildStatus.BUILD_FAILED);
                break;
            }
        }

    }


    /**
     * 准备阶段
     * @return 成功与否
     */
    private boolean prepare() {
        logger.info("进入准备阶段");
        if (buildHistoryId == null) {
            logger.error("buildHistoryId为null");
            return false;
        }
        // 设置基本信息
        buildHistory.setStartTime(LocalDateTime.now());
        buildHistory.setBuildInfoId(buildInfo.getId());
        buildHistory.setId(buildHistoryId);

        // 更新状态
        buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.PREPARING);

        return buildHistoryService.save(buildHistory);
    }

    /**
     * 更新资源
     * @return 成功与否
     */
    private boolean updateSource() {
        logger.info("正在更新代码");
        buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.BUILDING);

        try {
            PullResult pullResult = GitUtils.pull(buildInfo.getRepoId());
            return pullResult.isSuccessful();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 构建项目
     * 目前只支持Maven
     * @return 成功与否
     */
    private boolean build() {
        logger.info("开始构建");
        String repoId = buildInfo.getRepoId();

        try {
            mavenInvokerBuilder.build(buildInfo.getRepoId(), buildInfo.getBuildCommand());
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            logger.error("构建时出现错误:{}",e.getMessage());
            return false;
        }

        buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.BUILD_COMPLETED);
        logger.info("构建成功");

        // 将jar移动到指定目录便于管理
        ArrosProperties arrosProperties = SpringUtil.getBean(ArrosProperties.class);
        File gitRepoPath = new File(buildInfo.getResultPath());
        String[] fileList = gitRepoPath.list();
        Objects.requireNonNull(fileList, "文件夹为空");

        // TODO：查找jar的方式有待改进
        String reg = "^(?!original-).*\\.jar";
        String jarName = null;
        for (String file : fileList) {
            if (ReUtil.isMatch(reg,file)) {
                jarName = file;
                break;
            }
        }
        Objects.requireNonNull(jarName,"未找到Jar包");
        File jarPath = FileUtil.file(gitRepoPath, jarName);
        File targetPath = FileUtil.file(arrosProperties.getConfig(ConfigType.BUILD_PATH),
                buildInfo.getId(),
                buildHistory.getId(),
                jarName);
        File resultPath = FileUtil.copy(jarPath, targetPath, true);

        logger.info("移动jar包至：{}", resultPath.getAbsolutePath());
        buildHistory.setResultName(resultPath.getAbsolutePath());
        buildHistoryService.updateById(buildHistory);

        return true;
    }

    private boolean deploy(){
        logger.info("开始部署");
        // 部署
        if (Objects.equals(buildInfo.getDeployType(), DeployType.DEPLOY_TO_HOST.getType())) {
            new DeployService(buildInfo, buildHistory).run();
            buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.DEPLOYING);
        } else {
            // 不部署
            buildHistory.setEndTime(LocalDateTime.now());
            buildHistoryService.updateById(buildHistory);
            buildHistoryService.updateBuildStatus(buildHistory.getId(), BuildStatus.BUILD_COMPLETED);
        }

        return true;
    }
}
