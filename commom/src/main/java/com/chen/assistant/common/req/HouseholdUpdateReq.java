package com.chen.assistant.common.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class HouseholdUpdateReq {
    @NotNull(message = "id不能为空")
    private Long memberId;

    @NotBlank(message = "房间不能为空")
    private String room;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HouseholdUpdateReq{");
        sb.append("memberId=").append(memberId);
        sb.append(", room='").append(room).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
