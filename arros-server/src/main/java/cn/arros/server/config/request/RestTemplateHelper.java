package cn.arros.server.config.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

/**
 * @author Zero
 * @date 2022/6/3 16:58
 * @description
 * @since 1.8
 **/
public class RestTemplateHelper {

    protected Logger logger = LoggerFactory.getLogger(RestTemplateHelper.class);

    /**
     * base64编码
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        String base64Str = "";
        try {
            byte[] data = str.getBytes(StandardCharsets.UTF_8);
            base64Str = Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Str;
    }

    /**
     * base64解码
     *
     * @param base64Str
     * @return
     */
    public static String decode(String base64Str) {
        String str = "";
        byte[] base64Data = Base64.getDecoder().decode(base64Str);
        try {
            str = new String(base64Data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public String SHA256(final String strText)
    {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public String SHA512(final String strText)
    {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     *
     * @param
     * @return
     */
    private String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    /**
     * 将路径函数替换
     *
     * @param url
     * @param pathParams
     * @return
     */
    public String buildPathParams(String url, Map<String, String> pathParams) {
        //处理路径参数
        if(url.indexOf("{") != -1 || url.indexOf("}") != -1 || pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                url = url.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
            }
        }
        logger.debug("替换路径变量: " + url);
        return url;
    }

    /**
     * 添加请求头
     *
     * @param headerParams
     * @return
     */
    public HttpHeaders buildHeaders(Map<String, String> headerParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.github.v3+json");
        for (Map.Entry<String, String> entry : headerParams.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }
        logger.debug("请求头：" + headers);
        return headers;
    }

    public String buildGetPath(String url, Map<String, String> params) {
        url += "?";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += (entry.getKey() + "=" + entry.getValue() + "&");
        }
        url = url.substring(0, url.length() - 1);
        logger.info("get请求路径: " + url);
        return url;
    }

    public HttpEntity buildEntity(Map<String, Object> body, HttpHeaders headers) {
        HttpEntity httpEntity = new HttpEntity(body, headers);
        return httpEntity;
    }


    public ResponseEntity<String> executePost(String url, HttpEntity entity, RestTemplate restTemplate) {
        logger.debug("post的路径是: "  + url);
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response;
    }

     public void executeDelete(String url, RestTemplate restTemplate) {
            logger.info("delete的路径是: "  + url);
            restTemplate.delete(url);
    }

    public void executePut(String url, RestTemplate restTemplate) {
            logger.debug("delete的路径是: "  + url);

    }


    public ResponseEntity<String> executeGet(String url, RestTemplate restTemplate) {
        logger.debug("get的路径是: "  + url);
        final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCodeValue() != 200) {
            logger.error("请求 "+ url + " 失败");
            return null;
        }
        return response;
    }
}
