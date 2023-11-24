package com.chen.assistant.business.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConfirmOrderSaveReq {

    /**
     * id
     */
    private Long id;

    /**
     * 会员id
     */
    @NotNull(message = "【会员id】不能为空")
    private Long memberId;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @NotNull(message = "【日期】不能为空")
    private Date date;

    /**
     * 宿舍名字
     */
    @NotBlank(message = "【宿舍名字】不能为空")
    private String roomName;

    /**
     * 宿舍楼层
     */
    @NotBlank(message = "【宿舍楼层】不能为空")
    private String floorsCode;

    /**
     * 座位号|01, 02|03|04
     */
    @NotBlank(message = "【座位号】不能为空")
    private String index;

    /**
     * 余票ID
     */
    @NotNull(message = "【余票ID】不能为空")
    private Long dateRoomTicketId;

    /**
     * 订单状态|枚举[ConfirmOrderStatusEnum]
     */
    @NotBlank(message = "【订单状态】不能为空")
    private String status;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorsCode() {
        return floorsCode;
    }

    public void setFloorsCode(String floorsCode) {
        this.floorsCode = floorsCode;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Long getDateRoomTicketId() {
        return dateRoomTicketId;
    }

    public void setDateRoomTicketId(Long dateRoomTicketId) {
        this.dateRoomTicketId = dateRoomTicketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", date=").append(date);
        sb.append(", roomName=").append(roomName);
        sb.append(", floorsCode=").append(floorsCode);
        sb.append(", index=").append(index);
        sb.append(", dateRoomTicketId=").append(dateRoomTicketId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
