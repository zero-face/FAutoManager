package cn.arros.server.entity.dto;

/**
 * @author Zero
 * @date 2022/6/4 19:45
 * @description
 * @since 1.8
 **/
public class HookConfig {

    private String url;

    private String content_type = "json";

    private String secret;

    private String insecure_ssl = "1";

    private String token;

    private String digest;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String contentType) {
        this.content_type = contentType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInsecure_ssl() {
        return insecure_ssl;
    }

    public void setInsecure_ssl(String insecureSsl) {
        this.insecure_ssl = insecureSsl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        return "HookConfig{" +
                "url='" + url + '\'' +
                ", content_type='" + content_type + '\'' +
                ", secret='" + secret + '\'' +
                ", insecure_ssl='" + insecure_ssl + '\'' +
                ", token='" + token + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}
