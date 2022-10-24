package com.ecui.ErnestsVilla.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CryptoHelper {
    private final ObjectMapper objectMapper;
    private final String AES_IV="Cryptography2022";
    private byte[] aesKeyBytes;

    private boolean isDecryptionDone=false;

    public CryptoHelper(){
        this.objectMapper=new ObjectMapper();
        this.objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    private String aesDecrypt(byte[] cipher){
        Cipher aesCipher;

        try {
            aesCipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

        SecretKeySpec secretKeySpec=new SecretKeySpec(this.aesKeyBytes,"AES");
        IvParameterSpec ivParameterSpec=new IvParameterSpec(AES_IV.getBytes(StandardCharsets.UTF_8));

        try {
            aesCipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return new String(aesCipher.doFinal(cipher)).trim();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String aesEncrypt(byte[] plainText){
        Cipher aesCipher;

        try {
            aesCipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

        SecretKeySpec secretKeySpec=new SecretKeySpec(this.aesKeyBytes,"AES");
        IvParameterSpec ivParameterSpec=new IvParameterSpec(AES_IV.getBytes(StandardCharsets.UTF_8));

        try {
            aesCipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return Base64.getEncoder().encodeToString(aesCipher.doFinal(plainText));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String rsaDecrypt(byte[] cipher,byte[] privateKey){
        Cipher aesCipher;
        KeyFactory keyFactory;

        try {
            aesCipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
            keyFactory=KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(privateKey);
        PrivateKey key;

        try {
            key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }

        try {
            aesCipher.init(Cipher.DECRYPT_MODE,key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return new String(aesCipher.doFinal(cipher)).trim();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
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
            String rsaPrivateKey,
            TypeReference<T> type
    ){
        var bodyCipherBytes= Base64.getDecoder().decode(bodyCipher);
        var ksCipherBytes= Base64.getDecoder().decode(ksCipher);

        String aesKey=this.rsaDecrypt(
                ksCipherBytes,
                Base64.getDecoder().decode(
                        this.getPureRsaPrivateKeyBase64(rsaPrivateKey)
                                .getBytes(StandardCharsets.UTF_8)));

        if(aesKey==null){
            return null;
        }

        // save AES key for encryption
        this.aesKeyBytes=aesKey.getBytes(StandardCharsets.UTF_8);

        String decryptedString=this.aesDecrypt(bodyCipherBytes);

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
        if(this.aesKeyBytes==null){
            throw new RuntimeException("必须完成请求解密才能加密响应数据");
        }

        if(this.isDecryptionDone){
            throw new RuntimeException("不能使用同一个CryptoHelper对象进行多次加密");
        }

        try {
            String encrypted= this.aesEncrypt(objectMapper.writeValueAsBytes(responseObj));
            this.isDecryptionDone=true;
            return encrypted;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
