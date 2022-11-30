package com.example.post_api.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public static String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        return bytesToHex(md.digest());
    }

    static private String bytesToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            builder.append(String.format("%02x", digest[i]));
        }
        return builder.toString();
    }
}
