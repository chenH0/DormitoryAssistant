package com.chen.assistant.business.resp;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

public class StorysQueryResp {
    private Long id;
    private String code;

    /**
     * 性别|枚举[TrainTypeEnum]
     */
    @NotBlank(message = "【性别】不能为空")
    private String type;

    /**
     * 宿舍楼名
     */
    @NotBlank(message = "【宿舍楼名】不能为空")
    private String name;

    /**
     * 宿舍楼名拼音
     */
    @NotBlank(message = "【宿舍楼名拼音】不能为空")
    private String namePinyin;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @NotBlank(message = "【宿舍楼名拼音】不能为空")
    private String floors;

    private Integer total;

    private int status;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StorysQueryResp{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", namePinyin='").append(namePinyin).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", floors='").append(floors).append('\'');
        sb.append(", total=").append(total);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
