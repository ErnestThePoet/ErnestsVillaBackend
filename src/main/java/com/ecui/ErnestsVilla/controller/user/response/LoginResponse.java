package com.ecui.ErnestsVilla.controller.user.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends SuccessMsgResponse {
    private String account;
    private String accessId;
    private String sessionId;

    public LoginResponse(String account, String accessId, String sessionId) {
        super();
        this.account = account;
        this.accessId = accessId;
        this.sessionId = sessionId;
    }

    public LoginResponse(String msg) {
        super(msg);
        this.account = "";
        this.accessId = "";
        this.sessionId = "";
    }
}
