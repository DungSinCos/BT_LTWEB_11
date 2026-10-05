<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Trang chủ - BookStore</title>

<h2 class="mb-4">Danh sách sách</h2>

<div class="row row-cols-1 row-cols-md-3 g-4">
    <c:forEach var="book" items="${pageResult.items}">
        <div class="col">
            <div class="card h-100 shadow-sm">
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/assets/uploads/${book.coverImage}" class="book-cover" alt="${book.title}">
                    </c:when>
                    <c:otherwise>
                        <img src="https://via.placeholder.com/300x220?text=No+Cover" class="book-cover" alt="no cover">
                    </c:otherwise>
                </c:choose>
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="${pageContext.request.contextPath}/book?id=${book.bookId}">
                            <c:out value="${book.title}"/>
                        </a>
                    </h5>
                    <p class="mb-1"><strong>Mã ISBN:</strong> <c:out value="${book.isbn}"/></p>
                    <p class="mb-1"><strong>Tác giả:</strong> <c:out value="${authorNamesMap[book.bookId]}"/></p>
                    <p class="mb-1"><strong>Publisher:</strong> <c:out value="${book.publisher}"/></p>
                    <p class="mb-1"><strong>Publisher_date:</strong> <c:out value="${book.formattedPublishDate}"/></p>
                    <p class="mb-1"><strong>Quantity:</strong> ${book.quantity}</p>
                    <p class="mb-0 rating-star">
                        &#9733; <fmt:formatNumber value="${avgRatingMap[book.bookId]}" maxFractionDigits="1"/>
                        (Review ${reviewCountMap[book.bookId]})
                    </p>
                </div>
                <%-- Footer card: nút thêm vào giỏ --%>
                <div class="card-footer bg-transparent border-top-0 pb-3 pt-0">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user and not sessionScope.user.admin and book.quantity > 0}">
                            <form method="post" action="${pageContext.request.contextPath}/cart" class="d-flex gap-2 mt-2">
                                <input type="hidden" name="action"  value="add">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                                <input type="hidden" name="qty"    value="1">
                                <a href="${pageContext.request.contextPath}/book?id=${book.bookId}"
                                   class="btn btn-outline-secondary btn-sm flex-grow-1">Chi tiết</a>
                                <button type="submit" class="btn btn-primary btn-sm flex-grow-1">
                                    <i class="bi bi-cart-plus"></i> Thêm giỏ
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex gap-2 mt-2">
                                <a href="${pageContext.request.contextPath}/book?id=${book.bookId}"
                                   class="btn btn-outline-secondary btn-sm flex-grow-1">Chi tiết</a>
                                <c:if test="${book.quantity <= 0}">
                                    <button class="btn btn-secondary btn-sm flex-grow-1" disabled>Hết hàng</button>
                                </c:if>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:if test="${empty pageResult.items}">
        <p class="text-muted">Chưa có sách nào trong hệ thống.</p>
    </c:if>
</div>

<!-- Phan trang -->
<nav class="mt-4">
    <ul class="pagination justify-content-center">
        <li class="page-item ${!pageResult.hasPrev ? 'disabled' : ''}">
            <a class="page-link" href="?page=${pageResult.currentPage - 1}">&laquo;</a>
        </li>
        <c:forEach begin="1" end="${pageResult.totalPages}" var="p">
            <li class="page-item ${p == pageResult.currentPage ? 'active' : ''}">
                <a class="page-link" href="?page=${p}">${p}</a>
            </li>
        </c:forEach>
        <li class="page-item ${!pageResult.hasNext ? 'disabled' : ''}">
            <a class="page-link" href="?page=${pageResult.currentPage + 1}">&raquo;</a>
        </li>
    </ul>
</nav>
