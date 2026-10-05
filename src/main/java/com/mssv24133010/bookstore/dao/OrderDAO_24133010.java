package com.mssv24133010.bookstore.dao;

import com.mssv24133010.bookstore.entity.Order_24133010;
import java.util.List;

/** DAO interface cho Order. */
public interface OrderDAO_24133010 {

    /** Lưu mới 1 đơn hàng (cascade sang OrderItem). */
    void save(Order_24133010 order);

    /** Tìm đơn hàng theo id. */
    Order_24133010 findById(int orderId);

    /** Danh sách tất cả đơn hàng của 1 user, sắp xếp mới nhất trước. */
    List<Order_24133010> findByUser(int userId);

    /** Danh sách đơn hàng của 1 user lọc theo status (null = tất cả). */
    List<Order_24133010> findByUserAndStatus(int userId, Order_24133010.Status status);

    /** Cập nhật trạng thái đơn hàng. */
    void update(Order_24133010 order);
}
