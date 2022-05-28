package cn.arros.plugin.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Verge
 * @Date 2021/12/4 16:19
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "arros.plugin")
public class CoreProperties {
    private boolean enable;

    private String host;

    private Integer port;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "CoreProperties{" +
                "enable=" + enable +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
