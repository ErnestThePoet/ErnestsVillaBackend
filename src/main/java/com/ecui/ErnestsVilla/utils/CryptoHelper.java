package com.ecui.ErnestsVilla.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CryptoHelper {
    private final String AES_CIPHER_TRANSFORMATION="AES/CBC/PKCS5Padding";
    private final String AES_KEY_ALGORITHM="AES";

    private final String RSA_CIPHER_TRANSFORMATION="RSA/ECB/PKCS1Padding";
    private final String RSA_KEY_ALGORITHM="RSA";

    public String aesDecrypt(byte[] cipher,byte[] key,byte[] iv){
        Cipher aesCipher;

        try {
            aesCipher=Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

        SecretKeySpec secretKeySpec=new SecretKeySpec(key,AES_KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);

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

    public String aesEncrypt(byte[] plainText,byte[] key,byte[] iv){
        Cipher aesCipher;

        try {
            aesCipher=Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }

        SecretKeySpec secretKeySpec=new SecretKeySpec(key,AES_KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);

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

    public String rsaDecrypt(byte[] cipher,byte[] privateKey){
        Cipher aesCipher;
        KeyFactory keyFactory;

        try {
            aesCipher=Cipher.getInstance(RSA_CIPHER_TRANSFORMATION);
            keyFactory=KeyFactory.getInstance(RSA_KEY_ALGORITHM);
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
}
