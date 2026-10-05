package com.mssv24133010.bookstore.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JPA Entity ánh xạ bảng order_items.
 * Mỗi dòng là 1 sách trong 1 đơn hàng, lưu lại đơn giá lúc mua.
 */
@Entity
@Table(name = "order_items")
public class OrderItem_24133010 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order_24133010 order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book_24133010 book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    /** Đơn giá tại thời điểm mua (snapshot). */
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    public OrderItem_24133010() {}

    public OrderItem_24133010(Order_24133010 order, Book_24133010 book, int quantity, BigDecimal unitPrice) {
        this.order     = order;
        this.book      = book;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }

    /** Thành tiền dòng này. */
    public BigDecimal getSubtotal() {
        if (unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // ---- getters / setters ----
    public Integer getItemId()                        { return itemId; }
    public void setItemId(Integer itemId)             { this.itemId = itemId; }
    public Order_24133010 getOrder()                  { return order; }
    public void setOrder(Order_24133010 order)        { this.order = order; }
    public Book_24133010 getBook()                    { return book; }
    public void setBook(Book_24133010 book)           { this.book = book; }
    public int getQuantity()                          { return quantity; }
    public void setQuantity(int quantity)             { this.quantity = quantity; }
    public BigDecimal getUnitPrice()                  { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice)    { this.unitPrice = unitPrice; }
}
