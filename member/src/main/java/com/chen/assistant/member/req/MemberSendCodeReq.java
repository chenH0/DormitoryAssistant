package com.chen.assistant.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberSendCodeReq {
    @NotBlank(message = "校园邮箱不能为空")
    @Pattern(regexp = ".+@stu\\.xidian\\.edu\\.cn$", message = "校园邮箱格式错误")
    private String mobile;

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
