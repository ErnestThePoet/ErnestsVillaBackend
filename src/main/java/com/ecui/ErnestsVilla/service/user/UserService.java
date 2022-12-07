package com.ecui.ErnestsVilla.service.user;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.user.response.LoginResponse;
import com.ecui.ErnestsVilla.dao.AccessIdRepository;
import com.ecui.ErnestsVilla.dao.UserRepository;
import com.ecui.ErnestsVilla.entity.AccessId;
import com.ecui.ErnestsVilla.entity.User;
import com.ecui.ErnestsVilla.utils.DateTimeHelper;
import com.ecui.ErnestsVilla.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessIdRepository accessIdRepository;

    public boolean checkPw(User user, String password) {
        return HashHelper.checkBCrypt(password, user.getPwHashed());
    }


    // returns null if invalid accessId or level unsatisfied
    public User getUser(String accessId) {
        if (accessId == null) {
            return null;
        }

        var accessIdObj = accessIdRepository.findByAccessIdHashed(
                HashHelper.sha256Base64(accessId)
        );

        if (accessIdObj.isEmpty()
                || DateTimeHelper.isExpired(accessIdObj.get().getAccessIdExpire())) {
            return null;
        }

        var user = userRepository.findByAccount(accessIdObj.get().getAccount());

        return user.orElse(null);
    }

    public SuccessMsgResponse signup(String account, String password) {
        var user = userRepository.findByAccount(account);

        if (user.isPresent()) {
            return new SuccessMsgResponse("账号已存在");
        }

        var createdUser = new User();
        createdUser.setAccount(account);
        createdUser.setPwHashed(HashHelper.bCrypt(password));
        createdUser.setSessionIdHashed("");

        userRepository.save(createdUser);

        return new SuccessMsgResponse();
    }

    public LoginResponse login(String account, String password, Boolean remember) {
        var user = userRepository.findByAccount(account);

        if (user.isPresent()) {
            String newSessionId = "";

            if (remember) {
                newSessionId = renewSessionIdExpire(account);
            }

            if (checkPw(user.get(), password)) {
                String accessId = addAccessIdExpire(account);

                // login is the time when we clean up expired accessIds
                accessIdRepository.deleteByAccessIdExpireLessThan(DateTimeHelper.getNow());

                return new LoginResponse(
                        user.get().getAccount(),
                        accessId,
                        newSessionId
                );
            } else {
                return new LoginResponse("登录密码错误");
            }
        } else {
            return new LoginResponse("登录账号不存在");
        }
    }

    public LoginResponse autoLogin(String sessionId) {
        if (sessionId.equals("")) {
            return new LoginResponse("");
        }

        var user = userRepository.findBySessionIdHashed(
                HashHelper.sha256Base64(sessionId));

        if (user.isPresent()
                && !DateTimeHelper.isExpired(user.get().getSessionIdExpire())) {
            String accessId = addAccessIdExpire(user.get().getAccount());

            // login is the time when we clean up expired accessIds
            accessIdRepository.deleteByAccessIdExpireLessThan(DateTimeHelper.getNow());

            return new LoginResponse(
                    user.get().getAccount(),
                    accessId,
                    ""
            );
        } else {
            return new LoginResponse("");
        }
    }

    public void logout(String sessionId) {
        if (sessionId.equals("")) {
            return;
        }

        var user = userRepository.findBySessionIdHashed(
                HashHelper.sha256Base64(sessionId));

        if (user.isPresent()) {
            user.get().setSessionIdHashed("");
            userRepository.save(user.get());
        }
    }

    public SuccessMsgResponse changePw(String account, String password, String newPassword) {
        var user = userRepository.findByAccount(account);

        if (user.isPresent()) {
            if (checkPw(user.get(), password)) {
                user.get().setPwHashed(HashHelper.bCrypt(newPassword));
                userRepository.save(user.get());
                return new SuccessMsgResponse();
            } else {
                return new SuccessMsgResponse("原密码错误");
            }
        } else {
            return new SuccessMsgResponse("登录账号不存在");
        }
    }

    private String getRandomString(String account) {
        byte[] randomBytes = new byte[64];

        new SecureRandom().nextBytes(randomBytes);

        return account + Base64.getEncoder().encodeToString(randomBytes);
    }

    private String renewSessionIdExpire(String account) {
        var user = userRepository.findByAccount(account);

        String sessionId = "";

        if (user.isPresent()) {
            sessionId = getRandomString(account);

            user.get().setSessionIdHashed(HashHelper.sha256Base64(sessionId));
            user.get().setSessionIdExpire(DateTimeHelper.getSessionIdExpire());
            userRepository.save(user.get());
        }

        return sessionId;
    }

    private String addAccessIdExpire(String account) {
        String accessId = getRandomString(account);
        String accessIdHashed = HashHelper.sha256Base64(accessId);

        while (accessIdRepository.existsByAccessIdHashed(accessIdHashed)) {
            accessId = getRandomString(account);
            accessIdHashed = HashHelper.sha256Base64(accessId);
        }

        AccessId accessIdObj = new AccessId();
        accessIdObj.setAccount(account);
        accessIdObj.setAccessIdHashed(accessIdHashed);
        accessIdObj.setAccessIdExpire(DateTimeHelper.getAccessIdExpire());

        accessIdRepository.save(accessIdObj);

        return accessId;
    }
}
