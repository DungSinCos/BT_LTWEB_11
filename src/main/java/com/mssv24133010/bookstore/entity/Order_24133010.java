package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity ánh xạ bảng orders.
 * Trạng thái đơn hàng (status):
 *   NEW          → Đơn hàng mới
 *   CONFIRMED    → Đã xác nhận
 *   PREPARING    → Chuẩn bị hàng
 *   SHIPPING     → Vận chuyển
 *   DELIVERING   → Giao hàng
 *   DELIVERED    → Đã giao
 *   CANCELLED    → Đơn hàng hủy
 *   RETURNED     → Đơn hàng hoàn
 */
@Entity
@Table(name = "orders")
public class Order_24133010 implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status {
        NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED;

        /** Nhãn tiếng Việt hiển thị cho người dùng. */
        public String getLabel() {
            return switch (this) {
                case NEW       -> "Đơn hàng mới";
                case CONFIRMED -> "Đã xác nhận";
                case PREPARING -> "Chuẩn bị hàng";
                case SHIPPING  -> "Vận chuyển";
                case DELIVERING -> "Giao hàng";
                case DELIVERED -> "Đã giao";
                case CANCELLED -> "Đơn hàng hủy";
                case RETURNED  -> "Đơn hàng hoàn";
            };
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User_24133010 user;

    /** Tổng tiền đơn hàng (tính lúc đặt). */
    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    /** Phương thức thanh toán – mặc định COD. */
    @Column(name = "payment_method", length = 20, nullable = false)
    private String paymentMethod = "COD";

    /** Trạng thái đơn hàng dưới dạng String trong DB. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private Status status = Status.NEW;

    /** Địa chỉ giao hàng (lưu lại lúc đặt). */
    @Column(name = "shipping_address", length = 500, columnDefinition = "nvarchar(500)")
    private String shippingAddress;

    /** Ghi chú khách hàng. */
    @Column(name = "note", length = 1000, columnDefinition = "nvarchar(1000)")
    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem_24133010> orderItems = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Order_24133010() {}

    // ---- getters / setters ----
    public Integer getOrderId()                          { return orderId; }
    public void setOrderId(Integer orderId)              { this.orderId = orderId; }
    public User_24133010 getUser()                       { return user; }
    public void setUser(User_24133010 user)              { this.user = user; }
    public BigDecimal getTotalAmount()                   { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount)   { this.totalAmount = totalAmount; }
    public String getPaymentMethod()                     { return paymentMethod; }
    public void setPaymentMethod(String pm)              { this.paymentMethod = pm; }
    public Status getStatus()                            { return status; }
    public void setStatus(Status status)                 { this.status = status; }
    public String getShippingAddress()                   { return shippingAddress; }
    public void setShippingAddress(String a)             { this.shippingAddress = a; }
    public String getNote()                              { return note; }
    public void setNote(String note)                     { this.note = note; }
    public LocalDateTime getCreatedAt()                  { return createdAt; }
    public void setCreatedAt(LocalDateTime t)            { this.createdAt = t; }
    public LocalDateTime getUpdatedAt()                  { return updatedAt; }
    public void setUpdatedAt(LocalDateTime t)            { this.updatedAt = t; }
    public List<OrderItem_24133010> getOrderItems()      { return orderItems; }
    public void setOrderItems(List<OrderItem_24133010> l) { this.orderItems = l; }
}
