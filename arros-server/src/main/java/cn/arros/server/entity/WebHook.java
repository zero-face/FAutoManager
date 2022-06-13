package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Zero
 * @date 2022/6/4 20:38
 * @description
 * @since 1.8
 **/
@TableName("web_hook")
public class WebHook extends BaseEntity implements Serializable {
    @ApiModelProperty("主键配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String repoId;

    private String url;

    private String events;

    private String contentType;

    private String secret;

    private String insecureSsl = "1";

    private String token;

    private String digest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInsecureSsl() {
        return insecureSsl;
    }

    public void setInsecureSsl(String insecureSsl) {
        this.insecureSsl = insecureSsl;
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
        return "WebHook{" +
                "id=" + id +
                ", repoId='" + repoId + '\'' +
                ", url='" + url + '\'' +
                ", events='" + events + '\'' +
                ", contentType='" + contentType + '\'' +
                ", secret='" + secret + '\'' +
                ", insecureSsl='" + insecureSsl + '\'' +
                ", token='" + token + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}
