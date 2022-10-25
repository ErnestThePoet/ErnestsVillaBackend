package com.ecui.ErnestsVilla.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpCryptoHelper {
    private final CryptoHelper cryptoHelper;
    private final ObjectMapper objectMapper;
    private byte[] aesIvBytes;
    private byte[] aesKeyBytes;

    private boolean isDecryptionDone=false;

    public HttpCryptoHelper(){
        this.cryptoHelper=new CryptoHelper();
        this.objectMapper=new ObjectMapper();
        this.objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    


    private String getPureRsaPrivateKeyBase64(String fullKey){
        return fullKey.replace("BEGIN PRIVATE KEY","")
                .replace("END PRIVATE KEY","")
                .replace("-","")
                .replace("\r","")
                .replace("\n","");
    }

    /**
     * 解密电子信封加密后的请求体数据。
     *
     * @param   bodyCipher
     *          Base64编码的加密请求体数据。
     *
     * @param   ksCipher
     *          Base64编码的AES临时私钥。
     *
     * @param   ivCipher
     *          Base64编码的AES初始向量IV。
     *
     * @param   rsaPrivateKey
     *          RSA私钥字符串，即UTF-8编码的私钥PEM文件完整内容。
     *          以{@code -----BEGIN PRIVATE KEY-----}开头，后接私钥Base64编码串，
     *          并以{@code -----END PRIVATE KEY-----}结尾。
     *          也可以省略开头和结尾，只保留中间的Base64串。
     *
     * @param   type
     *          解密后希望得到的类型的TypeReference对象。
     *          例如，希望解密后得到{@code MyClass}类型，那么请传入
     *          {@code new TypeReference<MyClass>() {}}
     *
     * @return  一个和type指定类型一致的对象，或者null如果解密或反序列化过程出错。
     */
    public <T> T decryptRequestBody(
            String bodyCipher,
            String ksCipher,
            String ivCipher,
            String rsaPrivateKey,
            TypeReference<T> type
    ){
        var bodyCipherBytes= Base64.getDecoder().decode(bodyCipher);
        var ksCipherBytes= Base64.getDecoder().decode(ksCipher);
        var ivCipherBytes= Base64.getDecoder().decode(ivCipher);

        String aesKey=this.cryptoHelper.rsaDecrypt(
                ksCipherBytes,
                Base64.getDecoder().decode(
                        this.getPureRsaPrivateKeyBase64(rsaPrivateKey)
                                .getBytes(StandardCharsets.UTF_8)));

        if(aesKey==null){
            return null;
        }

        String aesIv=this.cryptoHelper.rsaDecrypt(
                ivCipherBytes,
                Base64.getDecoder().decode(
                        this.getPureRsaPrivateKeyBase64(rsaPrivateKey)
                                .getBytes(StandardCharsets.UTF_8)));

        if(aesIv==null){
            return null;
        }

        // save AES key and IV for encryption
        this.aesKeyBytes=aesKey.getBytes(StandardCharsets.UTF_8);
        this.aesIvBytes=aesIv.getBytes(StandardCharsets.UTF_8);

        String decryptedString=this.cryptoHelper.aesDecrypt(
                bodyCipherBytes,
                this.aesKeyBytes,
                this.aesIvBytes);

        try {
            return this.objectMapper.readValue(decryptedString,type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println(decryptedString);
            return null;
        }
    }

    /**
     * 加密响应体数据。注意必须完成相应请求的解密才能进行响应体加密，且调用此方法一次后
     * 便不能继续调用此方法。
     *
     * @param   responseObj
     *          响应体对象。
     *
     * @return  使用浏览器临时私钥加密的响应体密文字符串，或者null如果加密过程出错。
     */
    public String encryptResponseBody(Object responseObj){
        if(this.aesKeyBytes==null||this.aesIvBytes==null){
            throw new RuntimeException("必须成功完成请求解密才能加密响应数据");
        }

        if(this.isDecryptionDone){
            throw new RuntimeException("不能使用同一个CryptoHelper对象进行多次加密");
        }

        try {
            String encrypted= this.cryptoHelper.aesEncrypt(
                    objectMapper.writeValueAsBytes(responseObj),
                    this.aesKeyBytes,
                    this.aesIvBytes);
            this.isDecryptionDone=true;
            return encrypted;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
