package com.chen.assistant.business.req;

import com.chen.assistant.common.req.PageReq;

public class RoomCarriageQueryReq extends PageReq {
    private String floorCode;

    public String getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(String floorCode) {
        this.floorCode = floorCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomCarriageQueryReq{");
        sb.append("floorCode='").append(floorCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
