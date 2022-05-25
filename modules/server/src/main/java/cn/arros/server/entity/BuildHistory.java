package cn.arros.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@TableName("build_history")
@ApiModel(value = "BuildHistory对象", description = "")
public class BuildHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("构建信息ID")
    private String buildInfoId;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("产物路径")
    private String resultName;

    @ApiModelProperty("构建状态")
    private Integer status;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildInfoId() {
        return buildInfoId;
    }

    public void setBuildInfoId(String buildInfoId) {
        this.buildInfoId = buildInfoId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BuildHistory{" +
        "id=" + id +
        ", buildInfoId=" + buildInfoId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", resultName=" + resultName +
        ", status=" + status +
        "}";
    }
}
