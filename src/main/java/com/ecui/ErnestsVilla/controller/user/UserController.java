package com.ecui.ErnestsVilla.controller.user;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.user.response.LoginResponse;
import com.ecui.ErnestsVilla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup")
    public SuccessMsgResponse signup(
            @RequestParam String account,
            @RequestParam String bank1Account,
            @RequestParam String bank2Account,
            @RequestParam String password
    ) {
        return userService.signup(
                account,
                bank1Account,
                bank2Account,
                password);
    }

    @PutMapping(path = "/login")
    public LoginResponse login(
            @RequestParam String account,
            @RequestParam String password,
            @RequestParam Boolean remember
    ) {
        return userService.login(account, password, remember);
    }

    @PutMapping(path = "/auto_login")
    public LoginResponse autoLogin(
            @RequestParam String sessionId
    ) {
        return userService.autoLogin(sessionId);
    }

    @PutMapping(path = "/logout")
    public void logout(
            @RequestParam String sessionId
    ) {
        userService.logout(sessionId);
    }

    @PutMapping(path = "/change_pw")
    public SuccessMsgResponse changePw(
            @RequestParam String account,
            @RequestParam String password,
            @RequestParam String newPassword
    ) {
        return userService.changePw(account, password, newPassword);
    }
}
