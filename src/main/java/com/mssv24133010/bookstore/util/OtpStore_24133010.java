package com.mssv24133010.bookstore.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory store that holds the OTP code + pending registration data
 * (email, fullname, phone, hashed password) between the "register" step and the
 * "verify OTP" step. Keyed by e-mail. Entries expire after OTP_TTL_MS.
 */
public class OtpStore_24133010 {

    public static final long OTP_TTL_MS = 5 * 60 * 1000; // 5 phut

    public static class Entry {
        public final String otpCode;
        public final String fullname;
        public final String email;
        public final String phone;
        public final String hashedPassword;
        public final long createdAt;

        public Entry(String otpCode, String fullname, String email, String phone, String hashedPassword) {
            this.otpCode = otpCode;
            this.fullname = fullname;
            this.email = email;
            this.phone = phone;
            this.hashedPassword = hashedPassword;
            this.createdAt = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - createdAt > OTP_TTL_MS;
        }
    }

    private static final Map<String, Entry> STORE = new ConcurrentHashMap<>();

    private OtpStore_24133010() {}

    public static void put(String email, Entry entry) {
        STORE.put(email, entry);
    }

    public static Entry get(String email) {
        Entry e = STORE.get(email);
        if (e != null && e.isExpired()) {
            STORE.remove(email);
            return null;
        }
        return e;
    }

    public static void remove(String email) {
        STORE.remove(email);
    }

    public static String generateOtp() {
        int code = (int) (Math.random() * 900000) + 100000; // 6 chu so
        return String.valueOf(code);
    }
}
