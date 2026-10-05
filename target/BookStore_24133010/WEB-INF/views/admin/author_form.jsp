<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title><c:if test="${not empty author}">Sửa</c:if><c:if test="${empty author}">Thêm</c:if> tác giả - BookStore Admin</title>

<h3><c:if test="${not empty author}">Sửa tác giả</c:if><c:if test="${empty author}">Thêm tác giả mới</c:if></h3>

<form method="post" action="${pageContext.request.contextPath}/admin/authors">
    <c:if test="${not empty author}">
        <input type="hidden" name="authorId" value="${author.authorId}">
    </c:if>
    <div class="mb-3">
        <label class="form-label">Tên tác giả</label>
        <input type="text" name="author_name" class="form-control" value="${author.authorName}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Ngày sinh</label>
        <input type="date" name="date_of_birth" class="form-control" value="${author.dateOfBirth}">
    </div>
    <button type="submit" class="btn btn-primary">Lưu</button>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/authors">Hủy</a>
</form>
