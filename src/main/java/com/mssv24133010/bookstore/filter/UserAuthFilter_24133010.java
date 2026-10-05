package com.mssv24133010.bookstore.filter;

import com.mssv24133010.bookstore.entity.User_24133010;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Bảo vệ các route chỉ dành cho User đã đăng nhập:
 * /cart, /checkout, /orders, /order-detail
 */
@WebFilter(urlPatterns = {"/cart", "/checkout", "/orders", "/order-detail"})
public class UserAuthFilter_24133010 implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        User_24133010 user = (session != null) ? (User_24133010) session.getAttribute("user") : null;

        if (user == null) {
            // Lưu lại URL để redirect sau khi đăng nhập
            String target = request.getRequestURI();
            String query  = request.getQueryString();
            if (query != null) target += "?" + query;
            response.sendRedirect(request.getContextPath() + "/login?redirect=" +
                    java.net.URLEncoder.encode(target, "UTF-8"));
            return;
        }
        chain.doFilter(req, res);
    }
}
