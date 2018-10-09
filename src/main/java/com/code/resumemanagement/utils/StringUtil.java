package com.code.resumemanagement.utils;

import java.util.UUID;

public class StringUtil {


    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return true;
    }

}
