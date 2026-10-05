<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Quản lý Sách - BookStore Admin</title>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3>Quản lý Sách</h3>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/admin/books?action=new">+ Thêm sách</a>
</div>

<table class="table table-bordered table-hover bg-white">
    <thead class="table-dark">
    <tr>
        <th>ID</th><th>Cover</th><th>Tiêu đề</th><th>ISBN</th><th>Publisher</th><th>SL</th><th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${pageResult.items}">
        <tr>
            <td>${b.bookId}</td>
            <td>
                <c:if test="${not empty b.coverImage}">
                    <img src="${pageContext.request.contextPath}/assets/uploads/${b.coverImage}" style="width:50px;height:70px;object-fit:cover;">
                </c:if>
            </td>
            <td><c:out value="${b.title}"/></td>
            <td><c:out value="${b.isbn}"/></td>
            <td><c:out value="${b.publisher}"/></td>
            <td>${b.quantity}</td>
            <td>
                <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/admin/books?action=view&id=${b.bookId}">Xem</a>
                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/books?action=edit&id=${b.bookId}">Sửa</a>
                <a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/admin/books?action=delete&id=${b.bookId}"
                   onclick="return confirm('Xóa sách này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty pageResult.items}">
        <tr><td colspan="7" class="text-center text-muted">Không có dữ liệu.</td></tr>
    </c:if>
    </tbody>
</table>

<nav>
    <ul class="pagination">
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
