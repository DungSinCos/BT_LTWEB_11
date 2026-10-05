<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title><c:out value="${book.title}"/> - BookStore</title>

<c:if test="${empty book}">
    <p class="text-danger">Không tìm thấy sách.</p>
</c:if>

<c:if test="${not empty book}">
<div class="row">
    <div class="col-md-4">
        <c:choose>
            <c:when test="${not empty book.coverImage}">
                <img src="${pageContext.request.contextPath}/assets/uploads/${book.coverImage}" class="img-fluid rounded shadow-sm" alt="${book.title}">
            </c:when>
            <c:otherwise>
                <img src="https://via.placeholder.com/300x420?text=No+Cover" class="img-fluid rounded shadow-sm" alt="no cover">
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-md-8">
        <h2><c:out value="${book.title}"/></h2>
        <p><strong>Mã ISBN:</strong> <c:out value="${book.isbn}"/></p>
        <p><strong>Tác giả:</strong>
            <c:forEach var="a" items="${authors}" varStatus="st">
                <c:out value="${a.authorName}"/><c:if test="${!st.last}">, </c:if>
            </c:forEach>
        </p>
        <p><strong>Publisher:</strong> <c:out value="${book.publisher}"/></p>
        <p><strong>Publisher_date:</strong> <c:out value="${book.formattedPublishDate}"/></p>
        <p><strong>Quantity:</strong> ${book.quantity}</p>
        <p><strong>Giá:</strong> <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="đ"/></p>
        <p class="rating-star fs-5">&#9733; <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> (Reviews ${reviewCount})</p>
        <p><c:out value="${book.description}"/></p>

        <%-- Nút thêm vào giỏ --%>
        <c:choose>
            <c:when test="${not empty sessionScope.user and not sessionScope.user.admin}">
                <c:if test="${not empty sessionScope.cartWarning}">
                    <div class="alert alert-warning alert-dismissible fade show py-2" role="alert">
                        <c:out value="${sessionScope.cartWarning}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <c:remove var="cartWarning" scope="session"/>
                </c:if>
                <c:choose>
                    <c:when test="${book.quantity > 0}">
                        <form method="post" action="${pageContext.request.contextPath}/cart" class="d-flex align-items-center gap-2 mt-3">
                            <input type="hidden" name="action"  value="add">
                            <input type="hidden" name="bookId" value="${book.bookId}">
                            <input type="hidden" name="referer" value="${pageContext.request.contextPath}/book?id=${book.bookId}">
                            <input type="number" name="qty" value="1" min="1" max="${book.quantity}"
                                   class="form-control" style="width:80px;">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                            </button>
                            <a href="${pageContext.request.contextPath}/cart" class="btn btn-success">
                                <i class="bi bi-bag-check"></i> Xem giỏ hàng
                            </a>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger fw-bold mt-3"><i class="bi bi-x-circle"></i> Sách đã hết hàng</p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:when test="${empty sessionScope.user}">
                <p class="mt-3">
                    <a href="${pageContext.request.contextPath}/login?redirect=/book?id=${book.bookId}">Đăng nhập</a>
                    để thêm vào giỏ hàng.
                </p>
            </c:when>
        </c:choose>
    </div>
</div>

<hr class="my-4">

<h4>Reviews (${reviewCount})</h4>
<c:forEach var="r" items="${reviews}">
    <div class="border rounded p-3 mb-2">
        <strong><c:out value="${r.user.fullname}"/></strong>
        <span class="rating-star">
            <c:forEach begin="1" end="${r.rating}">&#9733;</c:forEach>
        </span>
        <p class="mb-0"><c:out value="${r.reviewText}"/></p>
    </div>
</c:forEach>
<c:if test="${empty reviews}">
    <p class="text-muted">Chưa có review nào.</p>
</c:if>

<h5 class="mt-4">Thêm review</h5>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <form method="post" action="${pageContext.request.contextPath}/book?id=${book.bookId}">
            <div class="mb-3">
                <label class="form-label">Đánh giá (1-5 sao)</label>
                <select name="rating" class="form-select" style="max-width:150px;">
                    <option value="5">5</option>
                    <option value="4">4</option>
                    <option value="3">3</option>
                    <option value="2">2</option>
                    <option value="1">1</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Nội dung review</label>
                <textarea name="review_text" class="form-control" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:when>
    <c:otherwise>
        <p><a href="${pageContext.request.contextPath}/login?redirect=/book?id=${book.bookId}">Đăng nhập</a> để viết review.</p>
    </c:otherwise>
</c:choose>
</c:if>
