package cn.arros.server.component;

import cn.arros.server.constant.ConfigType;
import cn.arros.server.properties.ArrosProperties;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.maven.shared.invoker.*;
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
    private final Invoker invoker;

    public MavenInvokerBuilder() {
        this.invoker = new DefaultInvoker().setMavenHome(new File(System.getenv("MAVEN_HOME")));
    }

    // 格式为 mvn package <command>
    public void build (String repoId, String command) throws MavenInvocationException {
        ArrosProperties arrosProperties = SpringUtil.getBean(ArrosProperties.class);
        String basePath = arrosProperties.getConfig(ConfigType.GIT).getConfigValue();

        InvocationRequest request = new DefaultInvocationRequest();
        request.setBaseDirectory(new File(basePath, repoId));

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
