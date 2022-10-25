package com.ecui.ErnestsVilla.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static final long MS_PER_HOUR=3600L*1000L;
    private static final long MS_PER_DAY=86400L*1000L;
    public static boolean isExpired(Date expire){
        return expire.before(getNow());
    }
    public static Date getNow(){
        return new Date();
    }

    public static Date getSessionIdExpire(){
        var sessionIdExpire= getNow();
        return new Date(sessionIdExpire.getTime()+7L*MS_PER_DAY);
    }

    public static Date getAccessIdExpire(){
        var accessIdExpire= getNow();
        return new Date(accessIdExpire.getTime()+24L*MS_PER_HOUR);
    }

    public static String toDisplayString(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static Date fromDisplayString(String s){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}
