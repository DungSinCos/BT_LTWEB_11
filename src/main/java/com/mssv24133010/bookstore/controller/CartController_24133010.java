package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.CartItem_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.service.BookService_24133010;
import com.mssv24133010.bookstore.service.CartService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * CartController_24133010 — /cart
 *
 * GET  /cart            → hiển thị giỏ hàng
 * POST /cart?action=add    → thêm sách (bookId, qty)
 * POST /cart?action=update → cập nhật số lượng (bookId, qty)
 * POST /cart?action=remove → xóa 1 sách (bookId)
 * POST /cart?action=clear  → xóa toàn bộ giỏ
 */
@WebServlet("/cart")
public class CartController_24133010 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartService_24133010 cartService = new CartService_24133010();
    private final BookService_24133010 bookService = new BookService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<CartItem_24133010> cart = cartService.getCart(session);

        req.setAttribute("cart", cart);
        req.setAttribute("cartTotal", cartService.getTotal(session));
        req.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        // Kiểm tra đăng nhập (filter đã bảo vệ nhưng phòng hờ)
        User_24133010 user = (User_24133010) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String bookIdStr = req.getParameter("bookId");
        int bookId = -1;
        try { bookId = Integer.parseInt(bookIdStr); } catch (Exception ignored) {}

        String warning = null;

        switch (action == null ? "" : action) {
            case "add" -> {
                int qty = 1;
                try { qty = Integer.parseInt(req.getParameter("qty")); } catch (Exception ignored) {}
                if (bookId > 0) {
                    Book_24133010 book = bookService.getBookById(bookId);
                    warning = cartService.addToCart(session, book, qty);
                }
            }
            case "update" -> {
                int qty = 1;
                try { qty = Integer.parseInt(req.getParameter("qty")); } catch (Exception ignored) {}
                if (bookId > 0) {
                    warning = cartService.updateQuantity(session, bookId, qty);
                }
            }
            case "remove" -> {
                if (bookId > 0) cartService.removeFromCart(session, bookId);
            }
            case "clear" -> cartService.clearCart(session);
        }

        // Trường hợp "add" từ trang chi tiết → redirect về trang chi tiết
        String referer = req.getParameter("referer");
        if (referer != null && !referer.isBlank() && "add".equals(action)) {
            if (warning != null) session.setAttribute("cartWarning", warning);
            resp.sendRedirect(referer);
            return;
        }

        if (warning != null) session.setAttribute("cartWarning", warning);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
