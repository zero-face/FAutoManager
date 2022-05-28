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
 */
public class HeartBeat{
    private final static Logger LOGGER = LoggerFactory.getLogger(HeartBeat.class);

    private static HeartBeat heartBeat;

    private final String serviceId;
    private final String serverHost;


    public HeartBeat(String serviceId, String serverHost) {
        this.serviceId = serviceId;
        this.serverHost = serverHost;
    }

    public static void init(HeartBeatBody heartBeatBody, String serverHost) {
        LOGGER.info("注册: " + heartBeatBody);

        String response = HttpRequest
                .post(serverHost + "/beat/register")
                .body(JSONUtil.toJsonStr(heartBeatBody))
                .execute()
                .body();
        JSONObject jsonObject = new JSONObject(response);
        String serviceId = jsonObject.getStr("data");
        heartBeat = new HeartBeat(serviceId, serverHost);

        LOGGER.info("注册成功");
    }

    public static HeartBeat getInstance() {
        return heartBeat;
    }

    public void beat() {
        LOGGER.debug("发送心跳");

        String response = HttpRequest
                .post(serverHost + "/beat")
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
}
