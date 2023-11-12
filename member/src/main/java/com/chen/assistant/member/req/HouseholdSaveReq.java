package com.chen.assistant.member.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class HouseholdSaveReq {
    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "学号不能为空")
    private String idCard;

    @NotBlank(message = "培养方式不能为空")
    private String type;

    private Date updateTime;

    @Min(value = 2000, message = "year不能小于1999")
    @Max(value = 2099, message = "year不能大于2100")
    private int year;

    @NotBlank(message = "school不能为空")
    private String school;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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
        final StringBuffer sb = new StringBuffer("HouseholdSaveReq{");
        sb.append("name='").append(name).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", year='").append(year).append('\'');
        sb.append(", school='").append(school).append('\'');
        sb.append('}');
        return sb.toString();
    }
}