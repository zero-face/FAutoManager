package cn.arros.plugin.core;

import cn.arros.common.dto.HeartBeatBody;
import cn.arros.plugin.core.component.HeartBeat;
import cn.arros.plugin.core.properties.CoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author Verge
 * @Refactor zero
 * @Description 当配置文件配置插件开启的时候会自动配置该类并且将配置内容映射到 CoreProperties.class
 * @Date 2021/12/4 16:14
 * @Version 1.0
 */

@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "arros.plugin", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(CoreProperties.class)
@EnableScheduling
public class AutoConfiguration {
    private final static Logger LOGGER = LoggerFactory.getLogger(HeartBeat.class);

    @Autowired
    private CoreProperties properties;

    @Autowired
    private Environment environment;

    /**
     * 初始化
     * 获取项目的端口、名称、IP、PID后向server进行注册
     * TODO：此处查询到的IP是本地IP
     */
    @PostConstruct
    public void init() {
        String port = environment.getProperty("server.port");
        String name = environment.getProperty("spring.application.name");
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage(),e);
        }
        assert localHost != null : "机器的IP地址为空";

        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        HeartBeatBody heartBeatBody = new HeartBeatBody(name, localHost.getHostAddress(), port, pid);
        HeartBeat.init(heartBeatBody, properties.getHost());
    }

    @Scheduled(fixedDelay = 10000)
    public void beat() {
        HeartBeat.getInstance().beat();
    }
}

