package com.chen.assistant.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberSendCodeReq {
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误")

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberSendCodeReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
