<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<title>Lịch sử đặt hàng - BookStore</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<h2 class="mb-4"><i class="bi bi-clock-history"></i> Lịch sử đặt hàng</h2>

<%-- Thông báo đặt hàng thành công --%>
<c:if test="${not empty orderSuccess}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill me-1"></i>
        <c:out value="${orderSuccess}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<%-- Tab lọc theo trạng thái --%>
<div class="mb-4">
    <ul class="nav nav-pills flex-wrap gap-1">
        <li class="nav-item">
            <a class="nav-link ${empty statusFilter ? 'active' : ''}"
               href="${pageContext.request.contextPath}/orders">
                Tất cả
            </a>
        </li>
        <c:forEach var="s" items="${allStatuses}">
            <li class="nav-item">
                <a class="nav-link ${statusFilter == s.name() ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/orders?status=${s.name()}">
                    <c:out value="${s.label}"/>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>

<%-- Danh sách đơn hàng --%>
<c:choose>
    <c:when test="${empty orders}">
        <div class="text-center py-5">
            <i class="bi bi-inbox fs-1 text-muted"></i>
            <p class="text-muted mt-3 fs-5">
                <c:choose>
                    <c:when test="${not empty statusFilter}">Không có đơn hàng nào ở trạng thái này.</c:when>
                    <c:otherwise>Bạn chưa có đơn hàng nào.</c:otherwise>
                </c:choose>
            </p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-2">Mua sắm ngay</a>
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach var="order" items="${orders}">
            <div class="card mb-3 shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div>
                        <strong>Đơn #${order.orderId}</strong>
                        <small class="text-muted ms-2">
                            <%-- Định dạng ngày giờ --%>
                            ${order.createdAt}
                        </small>
                    </div>
                    <%-- Badge trạng thái --%>
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
                <div class="card-body">
                    <%-- Danh sách sách trong đơn --%>
                    <c:forEach var="oi" items="${order.orderItems}">
                        <div class="d-flex align-items-center mb-2 pb-2 border-bottom">
                            <c:choose>
                                <c:when test="${not empty oi.book.coverImage}">
                                    <img src="${pageContext.request.contextPath}/assets/uploads/${oi.book.coverImage}"
                                         alt="${oi.book.title}" class="rounded me-3"
                                         style="width:50px;height:65px;object-fit:cover;">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://via.placeholder.com/50x65?text=No+Cover"
                                         alt="no cover" class="rounded me-3"
                                         style="width:50px;height:65px;object-fit:cover;">
                                </c:otherwise>
                            </c:choose>
                            <div class="flex-grow-1">
                                <div class="fw-semibold"><c:out value="${oi.book.title}"/></div>
                                <small class="text-muted">
                                    x${oi.quantity} ×
                                    <fmt:formatNumber value="${oi.unitPrice}" type="number" maxFractionDigits="0"/>đ
                                </small>
                            </div>
                            <div class="fw-bold text-danger">
                                <fmt:formatNumber value="${oi.subtotal}" type="number" maxFractionDigits="0"/>đ
                            </div>
                        </div>
                    </c:forEach>

                    <%-- Footer card --%>
                    <div class="d-flex justify-content-between align-items-center mt-2">
                        <div>
                            <small class="text-muted">
                                <i class="bi bi-geo-alt me-1"></i><c:out value="${order.shippingAddress}"/>
                            </small>
                            <br>
                            <small class="text-muted">
                                <i class="bi bi-cash-coin me-1"></i>${order.paymentMethod}
                            </small>
                        </div>
                        <div class="text-end">
                            <div class="fs-5 fw-bold text-danger mb-1">
                                Tổng: <fmt:formatNumber value="${order.totalAmount}" type="number" maxFractionDigits="0"/>đ
                            </div>
                            <a href="${pageContext.request.contextPath}/order-detail?id=${order.orderId}"
                               class="btn btn-outline-primary btn-sm">
                                <i class="bi bi-eye"></i> Xem chi tiết
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
