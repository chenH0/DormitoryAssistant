package com.chen.assistant.business.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomCarriageSaveReq {

    /**
     * id
     */
    private Long id;

    /**
     * 宿舍楼层编号
     */
    @NotBlank(message = "【宿舍楼层编号】不能为空")
    private String floorsCode;

    /**
     * 宿舍编号
     */
    @NotNull(message = "【宿舍编号】不能为空")
    private Integer index;

    /**
     * 床位类型|枚举[SeatTypeEnum]
     */
    @NotBlank(message = "【床位类型】不能为空")
    private String bedType;

    /**
     * 床位数
     */
    @NotNull(message = "【床位数】不能为空")
    private Integer bedCount;

    /**
     * 新增时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorsCode() {
        return floorsCode;
    }

    public void setFloorsCode(String floorsCode) {
        this.floorsCode = floorsCode;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomCarriageSaveReq{");
        sb.append("id=").append(id);
        sb.append(", floorsCode='").append(floorsCode).append('\'');
        sb.append(", index=").append(index);
        sb.append(", bedType='").append(bedType).append('\'');
        sb.append(", bedCount=").append(bedCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
