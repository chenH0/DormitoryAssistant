package com.chen.assistant.business.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RoomCarriageQueryResp implements Serializable {

    /**
     * id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String name;

    /**
     * 宿舍楼层编号
     */
    private String floorsCode;

    /**
     * 宿舍编号
     */
    private Integer index;

    /**
     * 床位类型|枚举[SeatTypeEnum]
     */
    private String bedType;

    /**
     * 床位数
     */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final StringBuffer sb = new StringBuffer("RoomCarriageQueryResp{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
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
