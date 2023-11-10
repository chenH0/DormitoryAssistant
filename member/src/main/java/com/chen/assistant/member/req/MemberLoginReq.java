package com.chen.assistant.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberLoginReq {
    @NotBlank(message = "邮箱不能为空")
    //@Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误")
    @Pattern(regexp = ".+@stu\\.xidian\\.edu\\.cn$", message = "校园邮箱格式错误")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberLoginReq{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
