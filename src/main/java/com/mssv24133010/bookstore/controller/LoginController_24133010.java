package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.service.UserService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Cau 2: dang nhap, su dung Session. Thanh cong -> trang chu; that bai -> quay lai trang dang nhap. */
@WebServlet("/login")
public class LoginController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24133010 userService = new UserService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/account/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String redirect = req.getParameter("redirect");

        User_24133010 user = userService.login(email, password);
        if (user == null) {
            req.setAttribute("error", "Email hoac mat khau khong dung.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/account/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);

        if (redirect != null && !redirect.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + redirect);
        } else if (user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
