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
 * @Description 当配置文件配置插件开启的时候会自动配置该类并且将配置内容映射到 CoreProperties.class同时准备心跳包定时发送出去
 * TODO 排除hutool中的发请求方式，太重
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

    private Boolean isRegistered;

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
        assert localHost != null : "机器的IP地址获取失败";

        //拿到pid
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        HeartBeatBody heartBeatBody = new HeartBeatBody(name, localHost.getHostAddress(), port, pid);
        //向服务端发起注册
        //TODO 注册不成功则重试几次
        HeartBeat.init(heartBeatBody, properties.getHost(), properties.getPort());
    }

    /**
     * 每隔10秒向服务端发出一个心跳
     */
    @Scheduled(fixedDelay = 10000)
    public void beat() {
        HeartBeat heartBeat = HeartBeat.getInstance();
        assert heartBeat != null : "服务尚未注册";
        heartBeat.beat();
    }
}

