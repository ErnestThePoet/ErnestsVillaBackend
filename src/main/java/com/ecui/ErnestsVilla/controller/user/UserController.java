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
            @RequestParam String password
    ) {
        return userService.signup(account, password);
    }

    @PostMapping(path = "/login")
    public LoginResponse login(
            @RequestParam String account,
            @RequestParam String password,
            @RequestParam Boolean remember
    ) {
        return userService.login(account, password, remember);
    }

    @PostMapping(path = "/auto_login")
    public LoginResponse autoLogin(
            @RequestParam String sessionId
    ) {
        return userService.autoLogin(sessionId);
    }

    @PostMapping(path = "/logout")
    public void logout(
            @RequestParam String sessionId
    ) {
        userService.logout(sessionId);
    }

    @PostMapping(path = "/change_pw")
    public SuccessMsgResponse changePw(
            @RequestParam String account,
            @RequestParam String password,
            @RequestParam String newPassword
    ) {
        return userService.changePw(account, password, newPassword);
    }
}
