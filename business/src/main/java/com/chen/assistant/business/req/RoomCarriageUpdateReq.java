package com.chen.assistant.business.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class RoomCarriageUpdateReq {

    private Long id;

    private String name;

    private int status;

    private String floorsCode;

    private String bedType;

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getFloorsCode() {
        return floorsCode;
    }

    public void setFloorsCode(String floorsCode) {
        this.floorsCode = floorsCode;
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

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomCarriageUpdateReq{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", status=").append(status);
        sb.append(", floorsCode='").append(floorsCode).append('\'');
        sb.append(", bedType='").append(bedType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
