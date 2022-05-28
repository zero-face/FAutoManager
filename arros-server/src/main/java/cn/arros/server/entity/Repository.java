package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Verge
 * @since 2021-11-01
 */
@ApiModel(value = "Repository对象", description = "")
public class Repository extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("uuid")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("仓库名称")
    private String name;

    @ApiModelProperty("仓库地址")
    private String gitUrl;

    @ApiModelProperty("登录用户名")
    private String username;

    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("公钥")
    private String rsaPub;

    @ApiModelProperty("私钥")
    private String rsaPrv;

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

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRsaPub() {
        return rsaPub;
    }

    public void setRsaPub(String rsaPub) {
        this.rsaPub = rsaPub;
    }

    public String getRsaPrv() {
        return rsaPrv;
    }

    public void setRsaPrv(String rsaPrv) {
        this.rsaPrv = rsaPrv;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gitUrl='" + gitUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rsaPub='" + rsaPub + '\'' +
                ", rsaPrv='" + rsaPrv + '\'' +
                '}';
    }
}
