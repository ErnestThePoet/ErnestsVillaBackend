package com.ecui.ErnestsVilla.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashHelper {
    public static String bCrypt(String message) {
        return BCrypt.hashpw(message, BCrypt.gensalt(12));
    }

    public static boolean checkBCrypt(String message, String sHashed) {
        return BCrypt.checkpw(message, sHashed);
    }

    public static byte[] sha256(byte[] message) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return md.digest(message);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] sha256(String message) {
        return sha256(message.getBytes(StandardCharsets.UTF_8));
    }

    public static String sha256Base64(byte[] message) {
        return Base64.getEncoder().encodeToString(sha256(message));
    }

    public static String sha256Base64(String message) {
        return Base64.getEncoder().encodeToString(sha256(message));
    }
}
