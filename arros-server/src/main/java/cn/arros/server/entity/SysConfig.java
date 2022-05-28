package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Zero
 * @date 2021/11/12 20:25
 * @description
 * @since 1.8
 **/
@TableName("sys_config")
@ApiModel(value = "SysConfig对象", description = "")
public class SysConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("配置键")
    private String configKey;

    @ApiModelProperty("配置名")
    private String configName;

    @ApiModelProperty("配置值")
    private String configValue;

    @ApiModelProperty("备注")
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "id=" + id +
                ", configKey='" + configKey + '\'' +
                ", configName='" + configName + '\'' +
                ", configValue='" + configValue + '\'' +
                ", remark='" + remark + '\'' +
                "} " + super.toString();
    }
}
