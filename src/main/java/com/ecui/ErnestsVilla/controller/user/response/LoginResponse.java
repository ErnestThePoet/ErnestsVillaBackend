package com.ecui.ErnestsVilla.controller.user.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends SuccessMsgResponse {
    private String account;
    private String bank1Account;
    private String bank2Account;
    private String accessId;
    private String sessionId;

    public LoginResponse(User user, String accessId, String sessionId) {
        super();
        this.account = user.getAccount();
        this.bank1Account = user.getBank1Account();
        this.bank2Account = user.getBank2Account();
        this.accessId = accessId;
        this.sessionId = sessionId;
    }

    public LoginResponse(String msg) {
        super(msg);
        this.account = "";
        this.bank1Account = "";
        this.bank2Account = "";
        this.accessId = "";
        this.sessionId = "";
    }
}
