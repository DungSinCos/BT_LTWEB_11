package com.mssv24133010.bookstore.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil_24133010 {

    private HashUtil_24133010() {}

    /** password column is varchar(32) -> store the 32-char hex MD5 digest. */
    public static String md5(String input) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
