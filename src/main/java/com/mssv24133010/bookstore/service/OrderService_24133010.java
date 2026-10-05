package com.mssv24133010.bookstore.service;

import com.mssv24133010.bookstore.dao.BookDAO_24133010;
import com.mssv24133010.bookstore.dao.BookDAOImpl_24133010;
import com.mssv24133010.bookstore.dao.OrderDAO_24133010;
import com.mssv24133010.bookstore.dao.OrderDAOImpl_24133010;
import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.CartItem_24133010;
import com.mssv24133010.bookstore.entity.Order_24133010;
import com.mssv24133010.bookstore.entity.OrderItem_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import java.math.BigDecimal;
import java.util.List;

/**
 * OrderService_24133010 — xử lý nghiệp vụ đặt hàng và tra cứu lịch sử.
 */
public class OrderService_24133010 {

    private final OrderDAO_24133010 orderDAO = new OrderDAOImpl_24133010();
    private final BookDAO_24133010  bookDAO  = new BookDAOImpl_24133010();

    /**
     * Tạo đơn hàng COD từ danh sách CartItem.
     * Trừ tồn kho của từng sách theo số lượng đặt.
     * Xóa giỏ hàng sau khi đặt thành công.
     *
     * @param user            người dùng đặt hàng
     * @param cartItems       danh sách item trong giỏ
     * @param shippingAddress địa chỉ giao hàng
     * @param note            ghi chú
     * @return Order vừa được tạo
     * @throws IllegalStateException khi tồn kho không đủ
     */
    public Order_24133010 placeOrder(User_24133010 user,
                                     List<CartItem_24133010> cartItems,
                                     String shippingAddress,
                                     String note) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống, không thể đặt hàng.");
        }

        Order_24133010 order = new Order_24133010();
        order.setUser(user);
        order.setPaymentMethod("COD");
        order.setStatus(Order_24133010.Status.NEW);
        order.setShippingAddress(shippingAddress);
        order.setNote(note);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem_24133010 ci : cartItems) {
            Book_24133010 book = bookDAO.findById(ci.getBookId());
            if (book == null) {
                throw new IllegalStateException("Sách (ID=" + ci.getBookId() + ") không tồn tại.");
            }
            int stock = (book.getQuantity() != null) ? book.getQuantity() : 0;
            if (stock < ci.getQuantity()) {
                throw new IllegalStateException(
                    "Sách \"" + book.getTitle() + "\" chỉ còn " + stock + " cuốn trong kho.");
            }

            // Trừ tồn kho
            book.setQuantity(stock - ci.getQuantity());
            bookDAO.update(book);

            BigDecimal unitPrice = ci.getUnitPrice() != null ? ci.getUnitPrice() : book.getPrice();
            OrderItem_24133010 oi = new OrderItem_24133010(order, book, ci.getQuantity(), unitPrice);
            order.getOrderItems().add(oi);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity())));
        }

        order.setTotalAmount(total);
        orderDAO.save(order);
        return order;
    }

    /** Lấy đơn hàng theo id. */
    public Order_24133010 getOrderById(int orderId) {
        return orderDAO.findById(orderId);
    }

    /** Tất cả đơn hàng của user. */
    public List<Order_24133010> getOrdersByUser(int userId) {
        return orderDAO.findByUser(userId);
    }

    /**
     * Lịch sử đơn hàng lọc theo trạng thái.
     *
     * @param userId user cần lấy
     * @param statusStr tên enum (null / "" = tất cả)
     * @return danh sách đơn hàng
     */
    public List<Order_24133010> getOrdersByUserAndStatus(int userId, String statusStr) {
        Order_24133010.Status status = null;
        if (statusStr != null && !statusStr.isBlank()) {
            try { status = Order_24133010.Status.valueOf(statusStr.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}
        }
        return orderDAO.findByUserAndStatus(userId, status);
    }
}
