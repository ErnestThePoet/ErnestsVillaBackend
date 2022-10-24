package com.ecui.ErnestsVilla.controller;

import com.ecui.ErnestsVilla.utils.CryptoHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @PostMapping(path = "/test")
    public void decTest(
            @RequestParam String bodyCipher,
            @RequestParam String ksCipher
    ){
        CryptoHelper helper=new CryptoHelper();

        String privKey;

        try {
            privKey= Files.readString(Path.of("./private.pem"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(helper.decryptRequestBody(bodyCipher,ksCipher,privKey));
    }
}
