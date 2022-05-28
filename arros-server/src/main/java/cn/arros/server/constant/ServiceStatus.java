package cn.arros.server.constant;

/**
 * @Author Verge
 * @Date 2021/12/5 0:24
 * @Version 1.0
 */
public enum ServiceStatus {
    ALIVE(1,"存活"),DEAD(0,"死亡");

    private final int code;
    private final String msg;

    ServiceStatus(int code, String msg) {
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
