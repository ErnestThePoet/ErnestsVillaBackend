package com.ecui.ErnestsVilla.controller.user.response.objs;

import lombok.Data;

@Data
public class UserData {
    private String account;
    private String name;

    public UserData(String account, String name) {
        this.account = account;
        this.name = name;
    }
}
