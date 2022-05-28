package cn.arros.common.dto;

/**
 * @Author Verge
 * @Date 2021/12/4 19:08
 * @Version 1.0
 */
public class HeartBeatBody {
    private String id;
    private String name;
    private String host;
    private String port;
    private String pid;

    public HeartBeatBody(String name, String host, String port, String pid) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.pid = pid;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "HeartBeatBody{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
