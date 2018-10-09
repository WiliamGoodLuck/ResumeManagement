package com.code.resumemanagement.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 得到当前系统日期.
     *
     * @return 当前日期的格式字符串, 日期格式为"yyyy-MM-dd"
     */
    public static String getCurrentDateyyyyMMdd() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    public static String getCurrentDateyyyyMMddhhmmss() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    public static String getCurrentYear() {
        String pattern = "yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getCurrentDateyyyyMMddhhmmss());
    }
}
