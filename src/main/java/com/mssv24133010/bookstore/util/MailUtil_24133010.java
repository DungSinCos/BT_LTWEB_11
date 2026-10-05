package com.mssv24133010.bookstore.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Sends the OTP activation code by e-mail using SMTP (e.g. Gmail SMTP with an App Password).
 * Configure SMTP_USER / SMTP_PASSWORD before running in production/demo.
 */
public class MailUtil_24133010 {

    private static final String SMTP_HOST = System.getProperty("smtp.host", "smtp.gmail.com");
    private static final String SMTP_PORT = System.getProperty("smtp.port", "587");
    private static final String SMTP_USER = System.getProperty("smtp.user", "your_email@gmail.com");
    private static final String SMTP_PASSWORD = System.getProperty("smtp.password", "your_app_password");

    private MailUtil_24133010() {}

    /**
     * Gui email OTP kich hoat. Trả về true nếu gửi thành công, false nếu thất bại.
     */
    public static boolean sendOtpEmail(String toEmail, String otpCode) {
        if ("your_email@gmail.com".equals(SMTP_USER) || "your_app_password".equals(SMTP_PASSWORD)) {
            System.err.println("Gui email OTP that bai: SMTP_USER / SMTP_PASSWORD chua duoc cau hinh thuc te.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER, "BookStore"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[BookStore] Ma xac thuc OTP dang ky tai khoan");
            message.setText("Xin chao,\n\nMa OTP kich hoat tai khoan cua ban la: " + otpCode +
                    "\nMa co hieu luc trong 5 phut.\n\nBookStore - MSSV 24133010");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.err.println("Gui email OTP that bai: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
