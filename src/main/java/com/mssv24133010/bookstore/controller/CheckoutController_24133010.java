package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.CartItem_24133010;
import com.mssv24133010.bookstore.entity.Order_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.service.CartService_24133010;
import com.mssv24133010.bookstore.service.OrderService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * CheckoutController_24133010 — /checkout
 *
 * GET  /checkout → hiển thị form xác nhận đơn hàng COD
 * POST /checkout → đặt hàng, trừ tồn kho, xóa giỏ, redirect về /orders
 */
@WebServlet("/checkout")
public class CheckoutController_24133010 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartService_24133010  cartService  = new CartService_24133010();
    private final OrderService_24133010 orderService = new OrderService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<CartItem_24133010> cart = cartService.getCart(session);

        if (cart.isEmpty()) {
            // Giỏ trống → về trang giỏ với cảnh báo
            session.setAttribute("cartWarning", "Giỏ hàng trống, không thể thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("cart", cart);
        req.setAttribute("cartTotal", cartService.getTotal(session));
        req.setAttribute("user", session.getAttribute("user"));
        req.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session  = req.getSession(false);
        User_24133010 user   = (User_24133010) session.getAttribute("user");
        List<CartItem_24133010> cart = cartService.getCart(session);

        if (cart.isEmpty()) {
            session.setAttribute("cartWarning", "Giỏ hàng trống.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String address = req.getParameter("shippingAddress");
        String note    = req.getParameter("note");

        // Validate địa chỉ
        if (address == null || address.isBlank()) {
            req.setAttribute("cart", cart);
            req.setAttribute("cartTotal", cartService.getTotal(session));
            req.setAttribute("user", user);
            req.setAttribute("error", "Vui lòng nhập địa chỉ giao hàng.");
            req.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(req, resp);
            return;
        }

        try {
            Order_24133010 order = orderService.placeOrder(user, cart, address, note);
            cartService.clearCart(session); // xóa giỏ sau khi đặt thành công
            session.setAttribute("orderSuccess", "Đặt hàng thành công! Mã đơn: #" + order.getOrderId());
            resp.sendRedirect(req.getContextPath() + "/orders");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            req.setAttribute("cart", cart);
            req.setAttribute("cartTotal", cartService.getTotal(session));
            req.setAttribute("user", user);
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(req, resp);
        }
    }
}
