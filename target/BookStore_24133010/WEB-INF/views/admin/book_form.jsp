<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title><c:if test="${not empty book}">Sửa</c:if><c:if test="${empty book}">Thêm</c:if> sách - BookStore Admin</title>

<h3><c:if test="${not empty book}">Sửa sách</c:if><c:if test="${empty book}">Thêm sách mới</c:if></h3>

<form method="post" action="${pageContext.request.contextPath}/admin/books" enctype="multipart/form-data">
    <c:if test="${not empty book}">
        <input type="hidden" name="bookId" value="${book.bookId}">
    </c:if>

    <div class="mb-3">
        <label class="form-label">Tiêu đề</label>
        <input type="text" name="title" class="form-control" value="${book.title}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">ISBN</label>
        <input type="text" name="isbn" class="form-control" value="${book.isbn}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Publisher</label>
        <input type="text" name="publisher" class="form-control" value="${book.publisher}">
    </div>
    <div class="row">
        <div class="col-md-4 mb-3">
            <label class="form-label">Giá</label>
            <input type="number" step="0.01" name="price" class="form-control" value="${book.price}">
        </div>
        <div class="col-md-4 mb-3">
            <label class="form-label">Số lượng</label>
            <input type="number" name="quantity" class="form-control" value="${book.quantity}">
        </div>
        <div class="col-md-4 mb-3">
            <label class="form-label">Ngày xuất bản</label>
            <input type="date" name="publish_date" class="form-control" value="${book.publishDate}">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label">Mô tả</label>
        <textarea name="description" class="form-control" rows="3">${book.description}</textarea>
    </div>
    <div class="mb-3">
        <label class="form-label">Ảnh bìa (cover_image)</label>
        <input type="file" name="coverImageFile" class="form-control" accept="image/*">
        <c:if test="${not empty book.coverImage}">
            <img src="${pageContext.request.contextPath}/assets/uploads/${book.coverImage}" style="width:80px;margin-top:8px;">
        </c:if>
    </div>
    <div class="mb-3">
        <label class="form-label">Tác giả</label>
        <select name="authorIds" class="form-select" multiple size="6">
            <c:forEach var="a" items="${allAuthors}">
                <option value="${a.authorId}"
                    <c:if test="${selectedAuthorIds.contains(a.authorId)}">selected</c:if>>
                    <c:out value="${a.authorName}"/>
                </option>
            </c:forEach>
        </select>
        <small class="text-muted">Giữ Ctrl (hoặc Cmd) để chọn nhiều tác giả.</small>
    </div>

    <button type="submit" class="btn btn-primary">Lưu</button>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/books">Hủy</a>
</form>
