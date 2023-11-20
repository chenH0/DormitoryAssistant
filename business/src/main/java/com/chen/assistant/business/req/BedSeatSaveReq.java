package com.chen.assistant.business.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BedSeatSaveReq {

    /**
     * id
     */
    private Long id;

    /**
     * 宿舍编号
     */
    @NotNull(message = "【宿舍编号】不能为空")
    private Long roomCode;

    /**
     * 宿舍名
     */
    @NotBlank(message = "【宿舍名】不能为空")
    private String roomName;

    private String floorsCode;

    /**
     * 排号|01, 02|03|04
     */
    @NotBlank(message = "【排号】不能为空")
    private String index;

    /**
     * 状态|枚举[0未占用，1已占用]
     */
    @NotNull(message = "【状态】不能为空")
    private Integer status;

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

    public String getFloorsCode() {
        return floorsCode;
    }

    public void setFloorsCode(String floorsCode) {
        this.floorsCode = floorsCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Long roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        final StringBuffer sb = new StringBuffer("BedSeatSaveReq{");
        sb.append("id=").append(id);
        sb.append(", roomCode=").append(roomCode);
        sb.append(", roomName='").append(roomName).append('\'');
        sb.append(", floorsCode='").append(floorsCode).append('\'');
        sb.append(", index='").append(index).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
