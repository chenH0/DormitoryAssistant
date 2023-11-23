package com.chen.assistant.business.req;

import com.chen.assistant.common.req.PageReq;

public class BedTicketQueryReq extends PageReq {
    private String trainCode;

    private int bedIndex;

    private int bedType;

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public int getBedIndex() {
        return bedIndex;
    }

    public void setBedIndex(int bedIndex) {
        this.bedIndex = bedIndex;
    }

    public String getBedType() {
        return String.valueOf(bedType);
    }

    public void setBedType(int bedType) {
        this.bedType = bedType;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BedTicketQueryReq{");
        sb.append("trainCode='").append(trainCode).append('\'');
        sb.append(", bedIndex=").append(bedIndex);
        sb.append(", bedType=").append(bedType);
        sb.append('}');
        return sb.toString();
    }
}
