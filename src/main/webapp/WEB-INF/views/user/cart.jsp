<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<title>Giỏ hàng - BookStore</title>

<h2 class="mb-4"><i class="bi bi-cart3"></i> Giỏ hàng của bạn</h2>

<%-- Thông báo cảnh báo số lượng --%>
<c:if test="${not empty sessionScope.cartWarning}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <c:out value="${sessionScope.cartWarning}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <c:remove var="cartWarning" scope="session"/>
</c:if>

<c:choose>
    <c:when test="${empty cart}">
        <div class="text-center py-5">
            <i class="bi bi-cart-x fs-1 text-muted"></i>
            <p class="text-muted mt-3 fs-5">Giỏ hàng của bạn đang trống.</p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-2">
                Tiếp tục mua sắm
            </a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th style="width:80px">Ảnh</th>
                        <th>Tên sách</th>
                        <th class="text-end">Đơn giá</th>
                        <th class="text-center" style="width:160px">Số lượng</th>
                        <th class="text-end">Thành tiền</th>
                        <th class="text-center" style="width:80px">Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cart}">
                        <tr>
                            <%-- Ảnh bìa --%>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty item.coverImage}">
                                        <img src="${pageContext.request.contextPath}/assets/uploads/${item.coverImage}"
                                             alt="${item.title}" class="rounded" style="width:60px;height:80px;object-fit:cover;">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://via.placeholder.com/60x80?text=No+Cover"
                                             alt="no cover" class="rounded" style="width:60px;height:80px;object-fit:cover;">
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <%-- Tên sách --%>
                            <td>
                                <a href="${pageContext.request.contextPath}/book?id=${item.bookId}"
                                   class="text-decoration-none fw-semibold">
                                    <c:out value="${item.title}"/>
                                </a>
                                <small class="d-block text-muted">Còn lại: ${item.maxQuantity} cuốn</small>
                            </td>

                            <%-- Đơn giá --%>
                            <td class="text-end">
                                <fmt:formatNumber value="${item.unitPrice}" type="number" maxFractionDigits="0"/>đ
                            </td>

                            <%-- Số lượng — form update --%>
                            <td class="text-center">
                                <form method="post" action="${pageContext.request.contextPath}/cart"
                                      class="d-flex align-items-center justify-content-center gap-1">
                                    <input type="hidden" name="action"  value="update">
                                    <input type="hidden" name="bookId" value="${item.bookId}">
                                    <button type="submit" name="qty" value="${item.quantity - 1}"
                                            class="btn btn-outline-secondary btn-sm px-2"
                                            ${item.quantity <= 1 ? 'disabled' : ''}>−</button>
                                    <input type="number" name="qty" value="${item.quantity}"
                                           min="1" max="${item.maxQuantity}"
                                           class="form-control form-control-sm text-center"
                                           style="width:60px;"
                                           onchange="this.form.submit()">
                                    <button type="submit" name="qty" value="${item.quantity + 1}"
                                            class="btn btn-outline-secondary btn-sm px-2"
                                            ${item.quantity >= item.maxQuantity ? 'disabled' : ''}>+</button>
                                </form>
                            </td>

                            <%-- Thành tiền --%>
                            <td class="text-end fw-bold text-danger">
                                <fmt:formatNumber value="${item.subtotal}" type="number" maxFractionDigits="0"/>đ
                            </td>

                            <%-- Xóa --%>
                            <td class="text-center">
                                <form method="post" action="${pageContext.request.contextPath}/cart">
                                    <input type="hidden" name="action"  value="remove">
                                    <input type="hidden" name="bookId" value="${item.bookId}">
                                    <button type="submit" class="btn btn-outline-danger btn-sm"
                                            title="Xóa"
                                            onclick="return confirm('Xóa sách này khỏi giỏ?')">
                                        <i class="bi bi-trash"></i> Xóa
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr class="table-light">
                        <td colspan="4" class="text-end fw-bold fs-5">Tổng cộng:</td>
                        <td class="text-end fw-bold fs-5 text-danger">
                            <fmt:formatNumber value="${cartTotal}" type="number" maxFractionDigits="0"/>đ
                        </td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <%-- Action buttons --%>
        <div class="d-flex justify-content-between mt-3">
            <%-- Xóa tất cả --%>
            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="btn btn-outline-secondary"
                        onclick="return confirm('Xóa toàn bộ giỏ hàng?')">
                    <i class="bi bi-trash3"></i> Xóa tất cả
                </button>
            </form>

            <div class="d-flex gap-2">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
                    <i class="bi bi-arrow-left"></i> Tiếp tục mua
                </a>
                <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success btn-lg px-4">
                    <i class="bi bi-credit-card"></i> Thanh toán COD
                </a>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%-- Bootstrap Icons --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
