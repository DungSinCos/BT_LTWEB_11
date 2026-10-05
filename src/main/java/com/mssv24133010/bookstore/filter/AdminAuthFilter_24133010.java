package com.mssv24133010.bookstore.filter;

import com.mssv24133010.bookstore.entity.User_24133010;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Chi cho phep user dang nhap voi is_admin = true truy cap vung /admin/*. */
@WebFilter("/admin/*")
public class AdminAuthFilter_24133010 implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        User_24133010 user = (session != null) ? (User_24133010) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + request.getRequestURI());
            return;
        }
        if (!user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        chain.doFilter(req, res);
    }
}
