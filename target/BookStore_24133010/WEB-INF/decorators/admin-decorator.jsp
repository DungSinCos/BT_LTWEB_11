<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title">Trang quản trị - BookStore</sitemesh:write></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <sitemesh:write property="head"/>
</head>
<body>
<nav class="navbar navbar-dark bg-dark mb-0">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/books">BookStore - Trang quản trị</a>
        <ul class="navbar-nav flex-row">
            <li class="nav-item me-3"><a class="nav-link" href="${pageContext.request.contextPath}/home">Về trang chủ</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
        </ul>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-md-block bg-light sidebar py-3">
            <div class="nav flex-column nav-pills">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/books">Quản lý Sách</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/authors">Quản lý Tác giả</a>
            </div>
        </nav>
        <main class="col-md-10 py-4">
            <sitemesh:write property="body"/>
        </main>
    </div>
</div>

<footer class="bg-dark text-light mt-4 py-3">
    <div class="container-fluid">
        <p class="mb-1">Họ tên: Lục Long Trí Dũng</p>
        <p class="mb-1">MSSV: 24133010</p>
        <p class="mb-0">Mã đề: 01</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
