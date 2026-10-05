package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.Order_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
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
 * OrderController_24133010 — /orders và /order-detail
 *
 * GET  /orders                → danh sách đơn hàng (lọc theo ?status=...)
 * GET  /order-detail?id=...   → chi tiết 1 đơn hàng
 */
@WebServlet(urlPatterns = {"/orders", "/order-detail"})
public class OrderController_24133010 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final OrderService_24133010 orderService = new OrderService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/order-detail".equals(path)) {
            showDetail(req, resp);
        } else {
            showList(req, resp);
        }
    }

    /** Hiển thị danh sách đơn hàng lọc theo trạng thái. */
    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User_24133010 user  = (User_24133010) session.getAttribute("user");

        String statusFilter = req.getParameter("status"); // có thể null → "ALL"

        List<Order_24133010> orders =
                orderService.getOrdersByUserAndStatus(user.getId(), statusFilter);

        req.setAttribute("orders", orders);
        req.setAttribute("statusFilter", statusFilter == null ? "" : statusFilter);
        req.setAttribute("allStatuses", Order_24133010.Status.values());

        // Lấy thông báo đặt hàng thành công (nếu có)
        String successMsg = (String) session.getAttribute("orderSuccess");
        if (successMsg != null) {
            req.setAttribute("orderSuccess", successMsg);
            session.removeAttribute("orderSuccess");
        }

        req.getRequestDispatcher("/WEB-INF/views/user/order_history.jsp").forward(req, resp);
    }

    /** Hiển thị chi tiết 1 đơn hàng. */
    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User_24133010 user  = (User_24133010) session.getAttribute("user");

        int orderId = -1;
        try { orderId = Integer.parseInt(req.getParameter("id")); } catch (Exception ignored) {}

        if (orderId <= 0) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        Order_24133010 order = orderService.getOrderById(orderId);

        // Bảo mật: chỉ được xem đơn hàng của mình
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/user/order_detail.jsp").forward(req, resp);
    }
}
