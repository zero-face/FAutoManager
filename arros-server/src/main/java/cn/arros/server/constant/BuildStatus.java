package cn.arros.server.constant;

/**
 * @Author Verge
 * @Date 2021/11/2 10:22
 * @Version 1.0
 */
public enum BuildStatus {
    PREPARING(0,"准备中"),
    BUILDING(1,"正在构建中"),
    BUILD_COMPLETED(2,"构建结束，等待发布"),
    BUILD_FAILED(3,"构建失败"),
    DEPLOYING(4,"正在部署中"),
    DEPLOY_COMPLETED(5,"部署完成"),
    DEPLOY_FAILED(6,"部署失败");

    private final int code;
    private final String msg;

    BuildStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
