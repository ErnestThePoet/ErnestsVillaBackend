package com.ecui.ErnestsVilla.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    public static final long MS_PER_SEC=1000L;
    public static final long MS_PER_MIN = 60L * MS_PER_SEC;
    public static final long MS_PER_HOUR = 3600L * MS_PER_SEC;
    public static final long MS_PER_DAY = 86400L * MS_PER_SEC;

    public static boolean isExpired(long expire) {
        return expire < getNow();
    }

    public static long getNow() {
        return new Date().getTime();
    }

    public static long getSessionIdExpire() {
        var sessionIdExpire = getNow();
        return sessionIdExpire + 7L * MS_PER_DAY;
    }

    public static long getAccessIdExpire() {
        var accessIdExpire = getNow();
        return accessIdExpire + 24L * MS_PER_HOUR;
    }

    public static String toDisplayString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static Date fromDisplayString(String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}
