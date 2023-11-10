package com.chen.assistant.member.resp;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class HouseholdResp {
    private Long memberId;

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "学号不能为空")
    private String idCard;

    @NotBlank(message = "name不能为空")
    private String type;

    private Date updateTime;

    @NotBlank(message = "years不能为空")
    private String years;

    @NotBlank(message = "school不能为空")
    private String school;


    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HouseholdResp{");
        sb.append("memberId=").append(memberId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", years='").append(years).append('\'');
        sb.append(", school='").append(school).append('\'');
        sb.append('}');
        return sb.toString();
    }
}