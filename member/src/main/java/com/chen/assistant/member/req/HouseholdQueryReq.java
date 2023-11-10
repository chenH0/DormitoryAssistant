package com.chen.assistant.member.req;

import com.chen.assistant.common.req.PageReq;

public class HouseholdQueryReq extends PageReq {
    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HouseholdQueryReq{");
        sb.append("memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}