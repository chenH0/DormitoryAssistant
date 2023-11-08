package com.chen.assistant.member.enums;

public enum HouseholdTypeEnum {
    BACHELOR("1", "本科生"),
    MASTER("2", "硕士生"),
    DOCTOR("3", "博士生");
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    HouseholdTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HouseholdTypeEnum{");
        sb.append("code='").append(code).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
