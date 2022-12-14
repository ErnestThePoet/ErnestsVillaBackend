package com.ecui.ErnestsVilla.service.objs;

public record Validity(boolean isValid,String message) {
    public Validity(){
        this(true,"");
    }
    public Validity(String message){
        this(false,message);
    }
}
