package com.chen.assistant.business.req;

import com.chen.assistant.common.req.PageReq;

public class BedSeatQueryReq extends PageReq {
    private String floorsCode;

    public String getFloorsCode() {
        return floorsCode;
    }

    public void setFloorsCode(String floorsCode) {
        this.floorsCode = floorsCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BedSeatQueryReq{");
        sb.append("floorsCode='").append(floorsCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
