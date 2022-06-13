package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String name;

    @ApiModelProperty("仓库地址")
    private String url;

    @ApiModelProperty("仓库地址")
    private String gitUrl;

    @ApiModelProperty("仓库地址")
    private String sshUrl;

    @ApiModelProperty("创建者")
    @NotBlank
    private String creator;

    @ApiModelProperty("所在组织名")
    private String orgName;

    @ApiModelProperty("仓库描述")
    private String description = "zeroPoint-studio development";

    @ApiModelProperty("仓库是否私有")
    private Boolean privateRepo = false;

    @ApiModelProperty("是否自动初始化")
    private Boolean autoInit = true;

    @ApiModelProperty("gitignore模板")
    private String gitignoreTemplate = "Java";

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String gitUrl) {
        this.url = gitUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivateRepo() {
        return privateRepo;
    }

    public void setPrivateRepo(Boolean privateRepo) {
        this.privateRepo = privateRepo;
    }

    public Boolean getAutoInit() {
        return autoInit;
    }

    public void setAutoInit(Boolean autoInit) {
        this.autoInit = autoInit;
    }

    public String getGitignoreTemplate() {
        return gitignoreTemplate;
    }

    public void setGitignoreTemplate(String gitignoreTemplate) {
        this.gitignoreTemplate = gitignoreTemplate;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", creator='" + creator + '\'' +
                ", orgName='" + orgName + '\'' +
                ", description='" + description + '\'' +
                ", privateRepo=" + privateRepo +
                ", autoInit=" + autoInit +
                ", gitignoreTemplate='" + gitignoreTemplate + '\'' +
                '}';
    }
}
