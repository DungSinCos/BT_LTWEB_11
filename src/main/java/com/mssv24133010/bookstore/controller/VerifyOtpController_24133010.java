package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.service.UserService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verify-otp")
public class VerifyOtpController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24133010 userService = new UserService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/account/verify_otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String otp = req.getParameter("otp");

        String error = userService.verifyOtpAndCreateUser(email, otp);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/account/verify_otp.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("message", "Kich hoat tai khoan thanh cong! Vui long dang nhap.");
        req.getRequestDispatcher("/WEB-INF/views/account/login.jsp").forward(req, resp);
    }
}
