package cn.arros.plugin.core.component;

import cn.arros.common.dto.HeartBeatBody;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author Verge
 * @Date 2021/12/4 17:31
 * @Version 1.0
 * @description 心跳类提供注册和发送心跳两个动作
 */
public class HeartBeat{
    private final static Logger LOGGER = LoggerFactory.getLogger(HeartBeat.class);

    private static HeartBeat heartBeat;

    private final String serviceId;

    private final String serverHost;

    private final Integer port;


    public HeartBeat(String serviceId, String serverHost, Integer port) {
        this.serviceId = serviceId;
        this.serverHost = serverHost;
        this.port = port;
    }

    public static void init(HeartBeatBody heartBeatBody, String serverHost, Integer port) {
        LOGGER.info("注册: " + heartBeatBody);

        String response = HttpRequest
                .post(serverHost + port + "/beat/register")
                .body(JSONUtil.toJsonStr(heartBeatBody))
                .execute()
                .body();
        JSONObject jsonObject = new JSONObject(response);
        String serviceId = jsonObject.getStr("data");
        //注册成功之后才单例生成一个心跳对象
        heartBeat = new HeartBeat(serviceId, serverHost, port);

        LOGGER.info("注册成功");
    }

    public static HeartBeat getInstance() {
        return heartBeat;
    }

    // TODO: 2022/5/28 添加失败重试机制：具体如何重试待考究！
    public void beat() {
        LOGGER.debug("发送心跳");

        String response = HttpRequest
                .post(serverHost + port + "/beat")
                .form("id", serviceId)
                .execute()
                .body();

        LOGGER.info(response);
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServerHost() {
        return serverHost;
    }
    public Integer getPort() {
        return port;
    }
}
