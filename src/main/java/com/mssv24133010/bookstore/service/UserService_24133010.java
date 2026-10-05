package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.dao.UserDAO_24133010;
import com.mssv24133010.bookstore.dao.UserDAOImpl_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.util.HashUtil_24133010;
import com.mssv24133010.bookstore.util.MailUtil_24133010;
import com.mssv24133010.bookstore.util.OtpStore_24133010;
import java.time.LocalDateTime;

public class UserService_24133010 {

    private final UserDAO_24133010 userDAO = new UserDAOImpl_24133010();

    /** Buoc 1 cua dang ky: kiem tra email, sinh OTP, gui mail, luu tam vao OtpStore (chua ghi DB). */
    public String startRegistration(String fullname, String email, String phone, String rawPassword) {
        if (userDAO.existsByEmail(email)) {
            return "Email da duoc su dung.";
        }
        String hashed = HashUtil_24133010.md5(rawPassword);
        String otp = OtpStore_24133010.generateOtp();
        boolean mailSent = MailUtil_24133010.sendOtpEmail(email, otp);
        if (!mailSent) {
            return "Gui email OTP that bai. Vui long kiem tra lai dia chi email hoac cau hinh SMTP server.";
        }
        OtpStore_24133010.put(email, new OtpStore_24133010.Entry(otp, fullname, email, phone, hashed));
        return null; // null = thanh cong
    }

    /** Buoc 2: xac thuc OTP, neu dung thi tao User that trong DB. */
    public String verifyOtpAndCreateUser(String email, String otpInput) {
        OtpStore_24133010.Entry entry = OtpStore_24133010.get(email);
        if (entry == null) {
            return "Ma OTP da het han hoac khong ton tai, vui long dang ky lai.";
        }
        if (!entry.otpCode.equals(otpInput.trim())) {
            return "Ma OTP khong dung.";
        }
        User_24133010 user = new User_24133010();
        user.setFullname(entry.fullname);
        user.setEmail(entry.email);
        user.setPhone(entry.phone);
        user.setPassword(entry.hashedPassword);
        user.setSignupDate(LocalDateTime.now());
        user.setAdmin(false);
        userDAO.save(user);
        OtpStore_24133010.remove(email);
        return null;
    }

    /** Tra ve User neu dang nhap dung, null neu sai. */
    public User_24133010 login(String email, String rawPassword) {
        User_24133010 user = userDAO.findByEmail(email);
        if (user == null) return null;
        if (!user.getPassword().equals(HashUtil_24133010.md5(rawPassword))) return null;
        user.setLastLogin(LocalDateTime.now());
        userDAO.update(user);
        return user;
    }

    public User_24133010 findById(int id) {
        return userDAO.findById(id);
    }
}
