package com.hhs.utils;

import java.util.UUID;

/**
 * @author ：何汉叁
 * @date ：2023/11/27 23:28
 * @description：生产唯一标识符
 */
public class UUIDUtil {

    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        str = str.replace("-", "");
        return str;
    }
}
