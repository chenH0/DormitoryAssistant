package com.chen.assistant.business.enums;

public enum RoomTypeEnum {
    LEFT("0", "左室"),
    MEDI("1", "中室"),
    RIGHT("0", "右室");
    private String code;
    private String desc;

    RoomTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String findEnumByCode(String code) {
        for (RoomTypeEnum statusEnum : RoomTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
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
