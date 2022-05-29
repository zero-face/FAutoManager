package cn.arros.plugin.core.component;

import cn.arros.common.dto.HeartBeatBody;
import cn.hutool.http.HttpException;
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

    //注册失败会重试三次
    public static boolean init(HeartBeatBody heartBeatBody, String serverHost, Integer port) throws Exception {
        LOGGER.info("注册到 " + serverHost + ":" + port + "/beat/register");
        LOGGER.info("注册信息 " + heartBeatBody);
        Object isRegistered = new RetryTemplate() {
            @Override
            protected Object doBiz() {
                try {
                    String response = null;
                    response = HttpRequest
                            .post(serverHost + ":" + port + "/beat/register")
                            .body(JSONUtil.toJsonStr(heartBeatBody))
                            .execute()
                            .body();
                    JSONObject jsonObject = new JSONObject(response);
                    String serviceId = jsonObject.getStr("data");
                    //注册成功后服务端会颁发一个唯一ID，然后用这个ID生成一个唯一的心跳对象
                    heartBeat = new HeartBeat(serviceId, serverHost, port);
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException("注册失败");
                }

            }
        }.setRetryTime(3).execute();
        LOGGER.info("是否注册成功：" + isRegistered);
        return (Boolean) isRegistered;

    }

    public static HeartBeat getInstance() {
        return heartBeat;
    }

    public void beat() {
        LOGGER.debug("发送心跳");

        String response = null;
        try {
            response = HttpRequest
                    .post(serverHost +":" + port + "/beat")
                    .form("id", serviceId)
                    .execute()
                    .body();
            LOGGER.debug(response);
        } catch (HttpException e) {
            LOGGER.error("发送心跳失败");
        }


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
