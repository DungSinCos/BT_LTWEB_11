<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<title>Thanh toán - BookStore</title>

<h2 class="mb-4"><i class="bi bi-bag-check"></i> Xác nhận đơn hàng</h2>

<%-- Lỗi --%>
<c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
</c:if>

<div class="row g-4">
    <%-- Cột trái: Thông tin giao hàng --%>
    <div class="col-md-7">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <i class="bi bi-truck"></i> Thông tin giao hàng
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/checkout" id="checkoutForm">

                    <%-- Thông tin người đặt (chỉ đọc) --%>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Họ tên</label>
                        <input type="text" class="form-control" value="${user.fullname}" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Email</label>
                        <input type="text" class="form-control" value="${user.email}" readonly>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Số điện thoại</label>
                        <input type="text" class="form-control" value="${user.phone}" readonly>
                    </div>

                    <%-- Địa chỉ giao hàng (bắt buộc) --%>
                    <div class="mb-3">
                        <label for="shippingAddress" class="form-label fw-semibold">
                            Địa chỉ giao hàng <span class="text-danger">*</span>
                        </label>
                        <textarea id="shippingAddress" name="shippingAddress" class="form-control" rows="3"
                                  placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"
                                  required>${param.shippingAddress}</textarea>
                    </div>

                    <%-- Ghi chú --%>
                    <div class="mb-3">
                        <label for="note" class="form-label">Ghi chú (tuỳ chọn)</label>
                        <textarea id="note" name="note" class="form-control" rows="2"
                                  placeholder="Ghi chú cho shop (nếu có)">${param.note}</textarea>
                    </div>

                    <%-- Phương thức thanh toán: COD cố định --%>
                    <div class="mb-4">
                        <label class="form-label fw-semibold">Phương thức thanh toán</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-cash-coin"></i></span>
                            <input type="text" class="form-control" value="COD — Thanh toán khi nhận hàng" readonly>
                        </div>
                    </div>

                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/cart"
                           class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Quay lại giỏ
                        </a>
                        <button type="submit" class="btn btn-success px-5">
                            <i class="bi bi-check-circle"></i> Đặt hàng ngay
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%-- Cột phải: Tóm tắt đơn hàng --%>
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white">
                <i class="bi bi-receipt"></i> Tóm tắt đơn hàng
            </div>
            <div class="card-body p-0">
                <ul class="list-group list-group-flush">
                    <c:forEach var="item" items="${cart}">
                        <li class="list-group-item">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <span class="badge bg-secondary me-1">${item.quantity}</span>
                                    <c:out value="${item.title}"/>
                                </div>
                                <span class="text-nowrap ms-2 fw-semibold">
                                    <fmt:formatNumber value="${item.subtotal}" type="number" maxFractionDigits="0"/>đ
                                </span>
                            </div>
                        </li>
                    </c:forEach>
                    <li class="list-group-item d-flex justify-content-between bg-light">
                        <strong>Phí vận chuyển:</strong>
                        <span class="text-success">Miễn phí</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between">
                        <strong class="fs-5">Tổng cộng:</strong>
                        <strong class="fs-5 text-danger">
                            <fmt:formatNumber value="${cartTotal}" type="number" maxFractionDigits="0"/>đ
                        </strong>
                    </li>
                </ul>
            </div>
        </div>

        <%-- COD Info box --%>
        <div class="alert alert-info mt-3">
            <i class="bi bi-info-circle-fill me-1"></i>
            <strong>COD:</strong> Bạn thanh toán bằng tiền mặt khi nhận được hàng. Không cần thẻ ngân hàng.
        </div>
    </div>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
