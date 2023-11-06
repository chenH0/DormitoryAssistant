package com.chen.assistant.common.util;

import cn.hutool.core.util.IdUtil;

public class SnowUtil {
    private static long dataCenter = 1;
    private static long workId = 1;
    public static long getSnowflaskNextId(){
        return IdUtil.getSnowflake(workId, dataCenter).nextId();
    }
    public static String getSnowflaskNextIdStr(){
        return IdUtil.getSnowflake(workId, dataCenter).nextIdStr();
    }
}
