package com.ecui.ErnestsVilla.controller;

import com.ecui.ErnestsVilla.utils.CryptoHelper;
import com.fasterxml.jackson.core.type.TypeReference;
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
    public String decTest(
            @RequestParam String bodyCipher,
            @RequestParam String ksCipher
    ){
        CryptoHelper helper=new CryptoHelper();
        // 获取证书私钥字符串，这里读文件作为演示
        String privateKey;
        try {
            privateKey= Files.readString(Path.of("./private.pem"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // request就是解密得到的实际请求对象
        TestRequest request=helper.decryptRequestBody(
                bodyCipher,
                ksCipher,
                privateKey,
                new TypeReference<TestRequest>(){}
        );

        // TODO 调用Service层执行操作

        // 返回格式为加密后的字符串
        return helper.encryptResponseBody(new TestResponse(true,""));
    }
}
