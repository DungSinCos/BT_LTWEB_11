# BookStore_24133010

Đề: LTWEB — Maven Project + Servlet + JPA + JSP (03 tầng: Presentation/MVC – Business – DAO), SiteMesh 2 vai trò, đăng ký OTP qua mail, CRUD sách/tác giả có phân trang.

Họ tên: Lục Long Trí Dũng — MSSV: 24133010 — Mã đề: 01

## 1. Cấu trúc project (3 tầng)

```
src/main/java/com/mssv24133010/bookstore/
  entity/       -> JPA entities (Book, User, Author, BookAuthor, Rating)
  dao/          -> Data Access Layer  (Tên_MSSV.java, ví dụ BookDAO_24133010)
  service/      -> Business Layer     (Tên_MSSV.java, ví dụ BookService_24133010)
  controller/   -> Presentation Layer (Servlet MVC, Tên_MSSV.java)
  controller/admin/ -> CRUD admin (BookAdminController_24133010, AuthorAdminController_24133010)
  filter/       -> EncodingFilter, AdminAuthFilter_24133010
  util/         -> JPAUtil, HashUtil, MailUtil, OtpStore, PageResult
src/main/webapp/WEB-INF/
  web.xml, sitemesh3.xml
  decorators/user-decorator.jsp   -> layout cho vai trò User
  decorators/admin-decorator.jsp  -> layout cho vai trò Admin
  views/...jsp
```

## 2. Cấu hình trước khi chạy

### a) Database (SQL Server)
Sửa `src/main/resources/META-INF/persistence.xml`:
- `jakarta.persistence.jdbc.url` — đổi `databaseName=BookStore` cho đúng DB của bạn.
- `jakarta.persistence.jdbc.user` / `jakarta.persistence.jdbc.password` — tài khoản SQL Server của bạn.

`hibernate.hbm2ddl.auto=update` sẽ tự tạo/cập nhật bảng theo entity nếu DB trống hoặc thiếu cột — vẫn khớp với cấu trúc bảng đã cho trong đề (books, users, author, book_author, rating).

### b) Gửi mail OTP
Sửa `src/main/java/com/mssv24133010/bookstore/util/MailUtil.java`:
- `SMTP_USER`, `SMTP_PASSWORD` — dùng Gmail thì tạo "App Password" (không dùng mật khẩu Gmail thường).

### c) Tài khoản Admin đầu tiên
Chưa có màn hình tạo admin (theo đề chỉ có đăng ký User qua OTP). Cách tạo admin đầu tiên:
1. Đăng ký 1 tài khoản bình thường qua `/register` (xác thực OTP để tài khoản được tạo trong bảng `users`).
2. Vào SQL Server, chạy: `UPDATE users SET is_admin = 1 WHERE email = 'ban@gmail.com';`
3. Đăng nhập lại — tài khoản này sẽ vào được `/admin/books`, `/admin/authors` và thấy menu "Trang quản trị".

## 3. Build & chạy

```
mvn clean package
```
Deploy file `target/BookStore_24133010.war` lên Tomcat 10+ (Jakarta namespace) / GlassFish 7+.
Truy cập: `http://localhost:8080/BookStore_24133010/home`

## 4. Chức năng theo từng câu

- **Câu 1**: Cấu trúc 3 tầng (entity/dao/service/controller), SiteMesh 2 decorator theo vai trò (`sitemesh3.xml` map `/admin/*` -> `admin-decorator.jsp`, còn lại -> `user-decorator.jsp`), header (Trang Chủ, Sản phẩm, Đăng nhập, Trang quản trị — chỉ admin thấy), footer (Họ tên/MSSV/Mã đề).
- **Câu 2**: `/register` (sinh OTP, gửi mail, chưa lưu DB) -> `/verify-otp` (đúng OTP mới tạo User trong DB) -> `/login` (session) -> `/logout`.
- **Câu 3**: `/home` — danh sách sách phân trang 6/trang (`BookService_24133010.PAGE_SIZE`).
- **Câu 4**: `/book?id=..` — chi tiết sách + danh sách review + form thêm review (yêu cầu đăng nhập).
- **Câu 6**: `/admin/books`, `/admin/authors` — CRUD đầy đủ (thêm/sửa/xóa/xem) + phân trang, bảo vệ bởi `AdminAuthFilter_24133010`. Class/Interface/Controller/Service đặt tên theo mẫu `Tên_24133010.java` như yêu cầu.
