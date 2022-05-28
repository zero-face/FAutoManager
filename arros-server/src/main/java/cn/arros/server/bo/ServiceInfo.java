package cn.arros.server.bo;

import cn.arros.server.constant.ServiceStatus;

/**
 * @Author Verge
 * @Date 2021/12/5 0:23
 * @Version 1.0
 */
public class ServiceInfo {
    private String name;
    private String host;
    private String port;
    private String pid;
    private Long ttl;
    private ServiceStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}