package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Verge
 * @since 2021-11-06
 */
@ApiModel(value = "Node对象", description = "")
public class Node extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("UUID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("节点名")
    private String name;

    @ApiModelProperty("登录节点的用户名")
    private String username;

    @ApiModelProperty("主机")
    private String host;

    @ApiModelProperty("端口")
    private Integer port;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("公钥")
    private String pubKey;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    @Override
    public String toString() {
        return "Node{" +
        "id=" + id +
        ", name=" + name +
        ", username=" + username +
        ", host=" + host +
        ", port=" + port +
        ", password=" + password +
        ", pubKey=" + pubKey +
        "}";
    }
}
