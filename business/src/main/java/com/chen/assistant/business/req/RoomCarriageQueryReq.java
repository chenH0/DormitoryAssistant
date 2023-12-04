package com.chen.assistant.business.req;

import com.chen.assistant.common.req.PageReq;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomCarriageQueryReq that = (RoomCarriageQueryReq) o;

        return Objects.equals(floorCode, that.floorCode)
                && Objects.equals(((RoomCarriageQueryReq) o).getPage(),that.getPage())
                && Objects.equals(((RoomCarriageQueryReq) o).getSize(),that.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorCode, getPage(), getSize());
    }
}
