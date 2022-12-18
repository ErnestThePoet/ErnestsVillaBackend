package com.ecui.ErnestsVilla.utils;

public class ItemHelper {
    public static String getShortItemName(String name,int length){
        if(name.length()<=length){
            return name;
        }
        else{
            return name.substring(0,length);
        }
    }

    public static String getShortItemName(String name){
        return getShortItemName(name,15);
    }
}
