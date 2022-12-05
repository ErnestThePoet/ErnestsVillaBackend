package com.ecui.ErnestsVilla.controller.user.response;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.user.response.objs.UserData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends SuccessMsgResponse {
    private UserData userData;
    private String accessId;
    private String sessionId;

    public LoginResponse(String account, String name, String accessId, String sessionId) {
        super();
        this.userData = new UserData(account, name);
        this.accessId = accessId;
        this.sessionId = sessionId;
    }

    public LoginResponse(String msg) {
        super(msg);
        this.userData = new UserData("", "");
        this.accessId = "";
        this.sessionId = "";
    }
}
