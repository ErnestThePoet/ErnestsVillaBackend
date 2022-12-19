package com.ecui.ErnestsVilla.service.user;

import com.ecui.ErnestsVilla.controller.common.response.SuccessMsgResponse;
import com.ecui.ErnestsVilla.controller.user.response.LoginResponse;
import com.ecui.ErnestsVilla.dao.AccessIdRepository;
import com.ecui.ErnestsVilla.dao.UserRepository;
import com.ecui.ErnestsVilla.entity.AccessId;
import com.ecui.ErnestsVilla.entity.User;
import com.ecui.ErnestsVilla.service.objs.Validity;
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

    private Validity checkUserValidity(User user, String password) {
        if (user.getAccount() == null
                || user.getAccount().length() < 3
                || user.getAccount().length() > 10
                || !user.getAccount().matches("^[\\da-zA-Z_-]+$")) {
            return new Validity("登录账号非法");
        }

        if (password == null
                || password.length() < 5
                || password.length() > 15) {
            return new Validity("登录密码非法");
        }

        if (user.getBank1Account() == null) {
            return new Validity("银行1账户不能为空");
        }

        if (user.getBank2Account() == null) {
            return new Validity("银行2账户不能为空");
        }

        return new Validity();
    }

    public boolean checkPw(User user, String password) {
        return HashHelper.checkBCrypt(password, user.getPwHashed());
    }


    // returns null if invalid accessId
    public User getUserWithAccessId(String accessId) {
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

    public SuccessMsgResponse signup(
            String account,
            String bank1Account,
            String bank2Account,
            String password) {
        if (userRepository.existsByAccount(account)) {
            return new SuccessMsgResponse("账号已存在");
        }

        if (userRepository.existsByBank1Account(bank1Account)) {
            return new SuccessMsgResponse("YYH Bank银行账号已被其他账号绑定");
        }

        if (userRepository.existsByBank2Account(bank2Account)) {
            return new SuccessMsgResponse("HIT Bank银行账号已被其他账号绑定");
        }

        var createdUser = new User();
        createdUser.setAccount(account);
        createdUser.setBank1Account(bank1Account);
        createdUser.setBank2Account(bank2Account);
        createdUser.setPwHashed(HashHelper.bCrypt(password));
        createdUser.setSessionIdHashed("");

        var validity = checkUserValidity(createdUser, password);

        if (!validity.isValid()) {
            return new SuccessMsgResponse(validity.message());
        }

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

                return new LoginResponse(
                        user.get(),
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

            return new LoginResponse(
                    user.get(),
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
