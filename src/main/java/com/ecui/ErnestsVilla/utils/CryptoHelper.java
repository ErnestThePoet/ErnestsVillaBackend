package com.ecui.ErnestsVilla.utils;

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
    private final String AES_IV="Cryptography2022";
    private byte[] aesKeyBytes=null;

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

    public String decryptRequestBody(
            String bodyCipherBase64,
            String ksCipherBase64,
            String rsaPrivateKeyBase64
    ){
        var bodyCipherBytes= Base64.getDecoder().decode(bodyCipherBase64);
        var ksCipherBytes= Base64.getDecoder().decode(ksCipherBase64);

        String aesKey=this.rsaDecrypt(
                ksCipherBytes,
                Base64.getDecoder().decode(
                        this.getPureRsaPrivateKeyBase64(rsaPrivateKeyBase64)
                                .getBytes(StandardCharsets.UTF_8)));

        if(aesKey==null){
            return null;
        }

        // save AES key for encryption
        this.aesKeyBytes=aesKey.getBytes(StandardCharsets.UTF_8);

        return this.aesDecrypt(bodyCipherBytes);
    }
}
