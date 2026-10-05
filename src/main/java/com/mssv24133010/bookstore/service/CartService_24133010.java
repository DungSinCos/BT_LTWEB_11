package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.CartItem_24133010;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * CartService_24133010 — quản lý giỏ hàng lưu trong Session.
 *
 * Giỏ hàng được lưu dưới khoá "cart" trong HttpSession dưới dạng
 * List<CartItem_24133010>. Tất cả thao tác (thêm/xóa/sửa số lượng)
 * đều được đồng bộ để thread-safe trong phạm vi 1 session.
 */
public class CartService_24133010 {

    private static final String CART_KEY = "cart";

    /** Lấy giỏ hàng hiện tại (tạo mới nếu chưa có). */
    @SuppressWarnings("unchecked")
    public List<CartItem_24133010> getCart(HttpSession session) {
        List<CartItem_24133010> cart = (List<CartItem_24133010>) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    /**
     * Thêm sách vào giỏ hoặc tăng số lượng nếu đã có.
     * Số lượng bị giới hạn bởi tồn kho (book.quantity).
     *
     * @param session  session hiện tại
     * @param book     sách cần thêm
     * @param qty      số lượng muốn thêm (>= 1)
     * @return thông điệp kết quả (null = thành công)
     */
    public String addToCart(HttpSession session, Book_24133010 book, int qty) {
        if (book == null) return "Sách không tồn tại.";
        if (qty < 1) qty = 1;

        List<CartItem_24133010> cart = getCart(session);
        int maxQty = (book.getQuantity() != null) ? book.getQuantity() : 0;

        synchronized (session) {
            // Tìm item đã tồn tại
            for (CartItem_24133010 item : cart) {
                if (item.getBookId().equals(book.getBookId())) {
                    int newQty = item.getQuantity() + qty;
                    if (newQty > maxQty) {
                        newQty = maxQty;
                        item.setQuantity(newQty);
                        item.setMaxQuantity(maxQty);
                        return "Đã đạt giới hạn tồn kho (" + maxQty + " cuốn).";
                    }
                    item.setQuantity(newQty);
                    item.setMaxQuantity(maxQty);
                    return null; // thành công
                }
            }
            // Chưa có – thêm mới
            if (maxQty <= 0) return "Sách đã hết hàng.";
            if (qty > maxQty) qty = maxQty;
            cart.add(new CartItem_24133010(
                    book.getBookId(), book.getTitle(), book.getCoverImage(),
                    book.getPrice(), qty, maxQty));
        }
        return null;
    }

    /**
     * Cập nhật số lượng cho 1 item trong giỏ.
     * Nếu qty <= 0 → xóa item đó.
     * Nếu qty > tồn kho → clamp về tồn kho.
     *
     * @return thông điệp cảnh báo hoặc null nếu ổn
     */
    public String updateQuantity(HttpSession session, int bookId, int qty) {
        List<CartItem_24133010> cart = getCart(session);
        synchronized (session) {
            CartItem_24133010 target = null;
            for (CartItem_24133010 item : cart) {
                if (item.getBookId() == bookId) { target = item; break; }
            }
            if (target == null) return null;

            if (qty <= 0) {
                cart.remove(target);
                return null;
            }
            if (qty > target.getMaxQuantity()) {
                qty = target.getMaxQuantity();
                target.setQuantity(qty);
                return "Số lượng tối đa là " + qty + " cuốn.";
            }
            target.setQuantity(qty);
        }
        return null;
    }

    /** Xóa 1 sách khỏi giỏ hàng theo bookId. */
    public void removeFromCart(HttpSession session, int bookId) {
        List<CartItem_24133010> cart = getCart(session);
        synchronized (session) {
            cart.removeIf(item -> item.getBookId() == bookId);
        }
    }

    /** Xóa toàn bộ giỏ hàng (sau khi đặt hàng thành công). */
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_KEY);
    }

    /** Tổng tiền của toàn bộ giỏ hàng. */
    public BigDecimal getTotal(HttpSession session) {
        return getCart(session).stream()
                .map(CartItem_24133010::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Tổng số lượng sản phẩm trong giỏ (dùng hiển thị badge). */
    public int getCartCount(HttpSession session) {
        return getCart(session).stream()
                .mapToInt(CartItem_24133010::getQuantity)
                .sum();
    }
}
