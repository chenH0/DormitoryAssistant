package com.chen.assistant.business.enums;

public enum BedTypeEnum {
    AVAI(1, "未占用"),
    UNAVAI(1, "已占用");
    private int code;
    private String desc;

    BedTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String findEnumByCode(int code) {
        for (BedTypeEnum statusEnum : BedTypeEnum.values()) {
            if (statusEnum.getCode()==code) {
                //如果需要直接返回name则更改返回类型为String,return statusEnum.name;
                return statusEnum.desc;
            }
        }
        throw new IllegalArgumentException("code is invalid");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomTypeEnum{");
        sb.append("code='").append(code).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
