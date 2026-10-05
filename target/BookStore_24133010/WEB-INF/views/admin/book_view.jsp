<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Chi tiết sách - BookStore Admin</title>

<h3>Chi tiết sách</h3>
<div class="row">
    <div class="col-md-3">
        <c:if test="${not empty book.coverImage}">
            <img src="${pageContext.request.contextPath}/assets/uploads/${book.coverImage}" class="img-fluid rounded">
        </c:if>
    </div>
    <div class="col-md-9">
        <p><strong>Tiêu đề:</strong> <c:out value="${book.title}"/></p>
        <p><strong>ISBN:</strong> <c:out value="${book.isbn}"/></p>
        <p><strong>Tác giả:</strong>
            <c:forEach var="a" items="${authors}" varStatus="st">
                <c:out value="${a.authorName}"/><c:if test="${!st.last}">, </c:if>
            </c:forEach>
        </p>
        <p><strong>Publisher:</strong> <c:out value="${book.publisher}"/></p>
        <p><strong>Ngày xuất bản:</strong> <c:out value="${book.formattedPublishDate}"/></p>
        <p><strong>Số lượng:</strong> ${book.quantity}</p>
        <p><strong>Giá:</strong> <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="đ"/></p>
        <p><strong>Mô tả:</strong> <c:out value="${book.description}"/></p>
    </div>
</div>
<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/books">Quay lại</a>
