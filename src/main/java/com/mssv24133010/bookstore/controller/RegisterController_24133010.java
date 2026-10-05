package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.service.UserService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Cau 2: trang dang ky, kich hoat bang OTP qua mail. */
@WebServlet("/register")
public class RegisterController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24133010 userService = new UserService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/account/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");

        String error = null;
        if (fullname == null || fullname.trim().isEmpty()) error = "Vui long nhap ho ten.";
        else if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) error = "Email khong hop le.";
        else if (password == null || password.length() < 6) error = "Mat khau phai co it nhat 6 ky tu.";
        else if (!password.equals(confirmPassword)) error = "Xac nhan mat khau khong khop.";

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/account/register.jsp").forward(req, resp);
            return;
        }

        error = userService.startRegistration(fullname, email, phone, password);
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/account/register.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("email", email);
        req.getRequestDispatcher("/WEB-INF/views/account/verify_otp.jsp").forward(req, resp);
    }
}
