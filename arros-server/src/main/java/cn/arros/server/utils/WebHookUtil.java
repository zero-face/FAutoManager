package cn.arros.server.utils;

import cn.arros.server.entity.dto.HookConfig;
import cn.arros.server.entity.dto.WebHookInfo;
import cn.arros.server.properties.ArrosProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zero
 * @date 2022/6/5 23:04
 * @description
 * @since 1.8
 **/
public class WebHookUtil {

    /**
     * 简单通用的webhook信息
     *
     * @param url
     * @param contentType
     * @param events
     * @return
     */
    public static WebHookInfo buildCommonHookInfo(String url, String contentType, String ...events) {
        return buildInfo(url, contentType,null,null,null,null,null, events);
    }

    public static WebHookInfo WithSecretHookInfo(String url, String secret, String contentType, String ...events) {
        return buildInfo(url, contentType,secret,null,null,null,null, events);
    }

    public static WebHookInfo buildJsonWebHook(String url, String secret, String insecure_ssl, String token,
                                        String digest, Boolean active, String ...events) {
        return buildInfo(url, "json", secret, insecure_ssl, token, digest, active, events);
    }


    private static WebHookInfo buildInfo(String url, String contentType, String secret, String insecure_ssl, String token,
                                         String digest, Boolean active, String... events) {
        final WebHookInfo webHookInfo = new WebHookInfo();
        final HookConfig hookConfig = new HookConfig();
        if(url != null) {
            hookConfig.setUrl(url);
        }
        if(contentType != null) {
            hookConfig.setContent_type(contentType);
        }
        if(secret != null) {
            hookConfig.setSecret(secret);
        }
        if(insecure_ssl != null) {
            hookConfig.setInsecure_ssl(insecure_ssl);
        }
        if(digest != null) {
            hookConfig.setDigest(digest);
        }
        if(token != null) {
            hookConfig.setToken(token);
        }
        if(active != null) {
            webHookInfo.setActive(active);
        }
        if(events != null && events.length != 0) {
            webHookInfo.setEvents(events);
        }
        webHookInfo.setHookConfig(hookConfig);
        return webHookInfo;
    }
}
