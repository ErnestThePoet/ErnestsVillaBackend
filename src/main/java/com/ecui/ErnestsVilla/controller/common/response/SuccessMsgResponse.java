package com.ecui.ErnestsVilla.controller.common.response;

import lombok.Data;

// 不要生成无参构造函数和全参构造函数，因为无参构造函数是我们自定义的
@Data
public class SuccessMsgResponse {
    private Boolean success;
    private String msg;

    public SuccessMsgResponse() {
        this.success = true;
        this.msg = "";
    }

    public SuccessMsgResponse(String msg) {
        this.success = false;
        this.msg = msg;
    }
}
