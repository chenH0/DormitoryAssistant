package com.chen.assistant.business.req;

import com.chen.assistant.common.req.PageReq;

public class BedSeatQueryReq extends PageReq {
    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BedSeatQueryReq{");
        sb.append("roomName='").append(roomName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
