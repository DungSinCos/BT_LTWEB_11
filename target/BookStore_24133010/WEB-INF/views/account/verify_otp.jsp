<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<title>Xác thực OTP - BookStore</title>

<div class="row justify-content-center">
    <div class="col-md-5">
        <h2 class="mb-4">Xác thực OTP</h2>
        <p>Mã OTP đã được gửi tới email <strong><c:out value="${email}"/></strong>. Mã có hiệu lực trong 5 phút.</p>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/verify-otp">
            <input type="hidden" name="email" value="${email}">
            <div class="mb-3">
                <label class="form-label">Mã OTP</label>
                <input type="text" name="otp" class="form-control" maxlength="6" required autofocus>
            </div>
            <button type="submit" class="btn btn-primary w-100">Kích hoạt tài khoản</button>
        </form>
        <p class="mt-3">Không nhận được mã? <a href="${pageContext.request.contextPath}/register">Đăng ký lại</a></p>
    </div>
</div>
