package cn.arros.server.controller.vo;

import cn.arros.server.bo.ServiceInfo;

/**
 * @Author Verge
 * @Date 2021/12/5 20:33
 * @Version 1.0
 */
public class ServiceInfoVO extends ServiceInfo {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ServiceInfoVO{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }
}
