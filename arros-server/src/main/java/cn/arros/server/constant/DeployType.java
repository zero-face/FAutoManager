package cn.arros.server.constant;

/**
 * @Author Verge
 * @Date 2021/11/16 20:02
 * @Version 1.0
 */
public enum DeployType {

    DO_NOTHING(0,"不处理"),
    DEPLOY_TO_HOST(1,"部署到主机");

    private Integer type;
    private String name;

    DeployType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }
}
