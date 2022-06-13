package cn.arros.server.component;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.log.SLF4JInvokerLogger;
import cn.arros.server.log.SLF4JOutputHandler;
import cn.arros.server.properties.ArrosProperties;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.maven.shared.invoker.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * @Author Verge
 * @Date 2021/11/16 20:38
 * @Version 1.0
 */
@Component
public class MavenInvokerBuilder {
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(MavenInvokerBuilder.class);

    private static final Logger logger = DEFAULT_LOGGER;

    private final Invoker invoker;

    public MavenInvokerBuilder() {
        this.invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("M2_HOME")));
        invoker.setLogger(new SLF4JInvokerLogger(logger));
        invoker.setOutputHandler(new SLF4JOutputHandler(logger));
        invoker.setErrorHandler(new SLF4JOutputHandler(logger));
    }

    // 格式为 mvn package <command>
    public void build (String repoId, String command) throws MavenInvocationException {
        ArrosProperties arrosProperties = SpringUtil.getBean(ArrosProperties.class);
        String basePath = arrosProperties.getConfig(ConfigType.REPO_PATH);

        InvocationRequest request = new DefaultInvocationRequest();
        //确定唯一仓库
        request.setBaseDirectory(new File(basePath, repoId));
        //设置打包命令
        request.setGoals(Arrays.asList("clean","package",command));

        InvocationResult result = invoker.execute(request);

        if (result.getExitCode() != 0) {
            if (result.getExecutionException() != null) {
                throw new MavenInvocationException("构建时出现错误",
                        result.getExecutionException());
            } else {
                throw new MavenInvocationException("构建时出现错误. Exit code: " +
                        result.getExitCode());
            }
        }
    }
}
