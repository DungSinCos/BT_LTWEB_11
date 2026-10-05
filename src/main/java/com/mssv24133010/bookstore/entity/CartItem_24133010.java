package com.mssv24133010.bookstore.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Đối tượng POJO lưu một dòng trong giỏ hàng (không lưu DB – lưu qua Session).
 * Mỗi CartItem giữ thông tin sách và số lượng người dùng chọn.
 */
public class CartItem_24133010 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer bookId;
    private String title;
    private String coverImage;
    private BigDecimal unitPrice;   // giá 1 cuốn
    private int quantity;           // số lượng trong giỏ
    private int maxQuantity;        // tồn kho tối đa (để validate giới hạn)

    public CartItem_24133010() {}

    public CartItem_24133010(Integer bookId, String title, String coverImage,
                              BigDecimal unitPrice, int quantity, int maxQuantity) {
        this.bookId     = bookId;
        this.title      = title;
        this.coverImage = coverImage;
        this.unitPrice  = unitPrice;
        this.quantity   = quantity;
        this.maxQuantity = maxQuantity;
    }

    /** Tổng tiền của dòng này (unitPrice * quantity). */
    public BigDecimal getSubtotal() {
        if (unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // ---- getters / setters ----
    public Integer getBookId()           { return bookId; }
    public void setBookId(Integer id)    { this.bookId = id; }

    public String getTitle()             { return title; }
    public void setTitle(String t)       { this.title = t; }

    public String getCoverImage()        { return coverImage; }
    public void setCoverImage(String c)  { this.coverImage = c; }

    public BigDecimal getUnitPrice()     { return unitPrice; }
    public void setUnitPrice(BigDecimal p) { this.unitPrice = p; }

    public int getQuantity()             { return quantity; }
    public void setQuantity(int q)       { this.quantity = q; }

    public int getMaxQuantity()          { return maxQuantity; }
    public void setMaxQuantity(int m)    { this.maxQuantity = m; }
}
