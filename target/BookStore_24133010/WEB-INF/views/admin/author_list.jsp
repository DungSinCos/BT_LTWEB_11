<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<title>Quản lý Tác giả - BookStore Admin</title>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3>Quản lý Tác giả</h3>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/admin/authors?action=new">+ Thêm tác giả</a>
</div>

<table class="table table-bordered table-hover bg-white">
    <thead class="table-dark">
    <tr><th>ID</th><th>Tên tác giả</th><th>Ngày sinh</th><th>Hành động</th></tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${pageResult.items}">
        <tr>
            <td>${a.authorId}</td>
            <td><c:out value="${a.authorName}"/></td>
            <td>${a.formattedDateOfBirth}</td>
            <td>
                <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/authors?action=edit&id=${a.authorId}">Sửa</a>
                <a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/admin/authors?action=delete&id=${a.authorId}"
                   onclick="return confirm('Xóa tác giả này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty pageResult.items}">
        <tr><td colspan="4" class="text-center text-muted">Không có dữ liệu.</td></tr>
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
