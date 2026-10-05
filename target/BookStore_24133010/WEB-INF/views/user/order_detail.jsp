<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<title>Chi tiết đơn hàng #${order.orderId} - BookStore</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<div class="d-flex align-items-center mb-4 gap-3">
    <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-secondary btn-sm">
        <i class="bi bi-arrow-left"></i> Quay lại
    </a>
    <h2 class="mb-0">Chi tiết đơn hàng #${order.orderId}</h2>
    <span class="badge fs-6 ${
        order.status.name() == 'NEW'        ? 'bg-secondary'  :
        order.status.name() == 'CONFIRMED'  ? 'bg-info text-dark' :
        order.status.name() == 'PREPARING'  ? 'bg-warning text-dark' :
        order.status.name() == 'SHIPPING'   ? 'bg-primary'    :
        order.status.name() == 'DELIVERING' ? 'bg-primary'    :
        order.status.name() == 'DELIVERED'  ? 'bg-success'    :
        order.status.name() == 'CANCELLED'  ? 'bg-danger'     :
        order.status.name() == 'RETURNED'   ? 'bg-dark'       : 'bg-secondary'
    }">
        <c:out value="${order.status.label}"/>
    </span>
</div>

<%-- Thanh tiến trình trạng thái --%>
<div class="card mb-4 shadow-sm">
    <div class="card-body">
        <div class="d-flex justify-content-between text-center flex-wrap gap-2" id="statusTracker">
            <%-- Chỉ hiển thị khi không phải hủy/hoàn --%>
            <c:if test="${order.status.name() != 'CANCELLED' && order.status.name() != 'RETURNED'}">
                <c:set var="statusOrder" value="NEW CONFIRMED PREPARING SHIPPING DELIVERING DELIVERED"/>
                <c:forEach var="s" items="${['NEW','CONFIRMED','PREPARING','SHIPPING','DELIVERING','DELIVERED']}">
                    <div class="flex-fill text-center">
                        <div class="rounded-circle mx-auto d-flex align-items-center justify-content-center
                            ${order.status.name() == s || 
                              (s == 'NEW' && true) ? 'bg-primary text-white' : 'bg-light border'}"
                             style="width:36px;height:36px;font-size:13px;">
                            <c:choose>
                                <c:when test="${s == 'NEW'}"><i class="bi bi-receipt"></i></c:when>
                                <c:when test="${s == 'CONFIRMED'}"><i class="bi bi-check2"></i></c:when>
                                <c:when test="${s == 'PREPARING'}"><i class="bi bi-box-seam"></i></c:when>
                                <c:when test="${s == 'SHIPPING'}"><i class="bi bi-truck"></i></c:when>
                                <c:when test="${s == 'DELIVERING'}"><i class="bi bi-person-walking"></i></c:when>
                                <c:when test="${s == 'DELIVERED'}"><i class="bi bi-house-check"></i></c:when>
                            </c:choose>
                        </div>
                        <small class="d-block mt-1 text-muted" style="font-size:11px;">
                            <c:choose>
                                <c:when test="${s == 'NEW'}">Đặt hàng</c:when>
                                <c:when test="${s == 'CONFIRMED'}">Xác nhận</c:when>
                                <c:when test="${s == 'PREPARING'}">Chuẩn bị</c:when>
                                <c:when test="${s == 'SHIPPING'}">Vận chuyển</c:when>
                                <c:when test="${s == 'DELIVERING'}">Giao hàng</c:when>
                                <c:when test="${s == 'DELIVERED'}">Đã giao</c:when>
                            </c:choose>
                        </small>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${order.status.name() == 'CANCELLED'}">
                <div class="text-center text-danger py-2 w-100">
                    <i class="bi bi-x-circle-fill fs-3"></i>
                    <p class="mb-0 fw-bold">Đơn hàng đã bị hủy</p>
                </div>
            </c:if>
            <c:if test="${order.status.name() == 'RETURNED'}">
                <div class="text-center text-dark py-2 w-100">
                    <i class="bi bi-arrow-return-left fs-3"></i>
                    <p class="mb-0 fw-bold">Đơn hàng đã được hoàn trả</p>
                </div>
            </c:if>
        </div>
    </div>
</div>

<div class="row g-4">
    <%-- Danh sách sản phẩm --%>
    <div class="col-md-7">
        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white">
                <i class="bi bi-list-ul"></i> Sản phẩm đã đặt
            </div>
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>Sách</th>
                            <th class="text-center">SL</th>
                            <th class="text-end">Đơn giá</th>
                            <th class="text-end">Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="oi" items="${order.orderItems}">
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center gap-2">
                                        <c:choose>
                                            <c:when test="${not empty oi.book.coverImage}">
                                                <img src="${pageContext.request.contextPath}/assets/uploads/${oi.book.coverImage}"
                                                     class="rounded" style="width:40px;height:55px;object-fit:cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://via.placeholder.com/40x55?text=N/A"
                                                     class="rounded" style="width:40px;height:55px;object-fit:cover;">
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="fw-semibold"><c:out value="${oi.book.title}"/></span>
                                    </div>
                                </td>
                                <td class="text-center">${oi.quantity}</td>
                                <td class="text-end">
                                    <fmt:formatNumber value="${oi.unitPrice}" type="number" maxFractionDigits="0"/>đ
                                </td>
                                <td class="text-end fw-bold text-danger">
                                    <fmt:formatNumber value="${oi.subtotal}" type="number" maxFractionDigits="0"/>đ
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr class="table-light">
                            <td colspan="3" class="text-end fw-bold">Tổng cộng:</td>
                            <td class="text-end fw-bold text-danger fs-5">
                                <fmt:formatNumber value="${order.totalAmount}" type="number" maxFractionDigits="0"/>đ
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>

    <%-- Thông tin đơn hàng --%>
    <div class="col-md-5">
        <div class="card shadow-sm mb-3">
            <div class="card-header bg-primary text-white">
                <i class="bi bi-info-circle"></i> Thông tin đơn hàng
            </div>
            <div class="card-body">
                <dl class="row mb-0">
                    <dt class="col-5">Mã đơn:</dt>
                    <dd class="col-7">#${order.orderId}</dd>

                    <dt class="col-5">Ngày đặt:</dt>
                    <dd class="col-7">${order.createdAt}</dd>

                    <dt class="col-5">Thanh toán:</dt>
                    <dd class="col-7">
                        <span class="badge bg-warning text-dark">
                            <i class="bi bi-cash-coin me-1"></i>${order.paymentMethod}
                        </span>
                    </dd>

                    <dt class="col-5">Trạng thái:</dt>
                    <dd class="col-7">
                        <span class="badge ${
                            order.status.name() == 'DELIVERED'  ? 'bg-success' :
                            order.status.name() == 'CANCELLED'  ? 'bg-danger'  :
                            order.status.name() == 'RETURNED'   ? 'bg-dark'    : 'bg-info text-dark'
                        }">
                            <c:out value="${order.status.label}"/>
                        </span>
                    </dd>

                    <dt class="col-5">Địa chỉ:</dt>
                    <dd class="col-7"><c:out value="${order.shippingAddress}"/></dd>

                    <c:if test="${not empty order.note}">
                        <dt class="col-5">Ghi chú:</dt>
                        <dd class="col-7"><c:out value="${order.note}"/></dd>
                    </c:if>
                </dl>
            </div>
        </div>

        <div class="alert alert-info">
            <i class="bi bi-headset me-1"></i>
            Cần hỗ trợ? Liên hệ chúng tôi qua email hoặc hotline.
        </div>
    </div>
</div>
