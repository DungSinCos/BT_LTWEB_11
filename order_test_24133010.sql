-- ============================================================================
-- SCRIPT TẠO BẢNG VÀ TEST TRẠNG THÁI ĐƠN HÀNG
-- BookStore_24133010 — Phần giỏ hàng / đặt hàng COD / lịch sử đơn hàng
-- Chạy sau khi deploy ứng dụng lần đầu (Hibernate tự tạo bảng orders & order_items)
-- ============================================================================

USE BookStore;
GO

-- ============================================================
-- 1. ĐỔI CỘT SANG NVARCHAR ĐỂ HỖ TRỢ TIẾNG VIỆT (nếu cần)
-- ============================================================
-- Hibernate đã tạo bảng. Nếu cần, chạy thêm:
-- ALTER TABLE orders ALTER COLUMN shipping_address NVARCHAR(500);
-- ALTER TABLE orders ALTER COLUMN note             NVARCHAR(1000);

-- ============================================================
-- 2. THÊM DỮ LIỆU MẪU ĐƠN HÀNG (để quan sát lọc trạng thái)
-- Các đơn hàng mẫu cho user id=2 (dung@gmail.com)
-- ============================================================

-- Xóa dữ liệu mẫu cũ (nếu có)
DELETE FROM order_items WHERE order_id IN (SELECT order_id FROM orders WHERE user_id = 2);
DELETE FROM orders WHERE user_id = 2;
GO

-- Chèn 8 đơn hàng mẫu — mỗi đơn 1 trạng thái khác nhau
INSERT INTO orders (user_id, total_amount, payment_method, status, shipping_address, note, created_at, updated_at)
VALUES
(2, 120.00, 'COD', 'NEW',        N'123 Nguyễn Văn A, Q1, TP.HCM',  N'Giao giờ hành chính', GETDATE(), GETDATE()),
(2, 255.00, 'COD', 'CONFIRMED',  N'456 Lê Thị B, Q3, TP.HCM',      N'',                     GETDATE(), GETDATE()),
(2,  85.00, 'COD', 'PREPARING',  N'789 Trần Văn C, Q7, TP.HCM',    N'Đóng gói cẩn thận',   GETDATE(), GETDATE()),
(2, 200.00, 'COD', 'SHIPPING',   N'101 Phạm Ngũ Lão, Q1, TP.HCM',  N'',                     GETDATE(), GETDATE()),
(2, 174.00, 'COD', 'DELIVERING', N'202 Bùi Viện, Q1, TP.HCM',      N'Gọi trước khi giao',  GETDATE(), GETDATE()),
(2, 150.00, 'COD', 'DELIVERED',  N'303 Võ Văn Tần, Q3, TP.HCM',    N'',                     GETDATE(), GETDATE()),
(2,  79.00, 'COD', 'CANCELLED',  N'404 Đinh Tiên Hoàng, Bình Thạnh, TP.HCM', N'Đổi ý',     GETDATE(), GETDATE()),
(2,  95.00, 'COD', 'RETURNED',   N'505 Cách Mạng Tháng 8, Q10, TP.HCM', N'Hàng lỗi',       GETDATE(), GETDATE());
GO

-- Chèn order_items mẫu cho các đơn trên
-- Lấy order_id vừa chèn
DECLARE @o1 INT, @o2 INT, @o3 INT, @o4 INT, @o5 INT, @o6 INT, @o7 INT, @o8 INT;

SELECT TOP 1 @o1 = order_id FROM orders WHERE user_id=2 AND status='NEW'        ORDER BY order_id DESC;
SELECT TOP 1 @o2 = order_id FROM orders WHERE user_id=2 AND status='CONFIRMED'  ORDER BY order_id DESC;
SELECT TOP 1 @o3 = order_id FROM orders WHERE user_id=2 AND status='PREPARING'  ORDER BY order_id DESC;
SELECT TOP 1 @o4 = order_id FROM orders WHERE user_id=2 AND status='SHIPPING'   ORDER BY order_id DESC;
SELECT TOP 1 @o5 = order_id FROM orders WHERE user_id=2 AND status='DELIVERING' ORDER BY order_id DESC;
SELECT TOP 1 @o6 = order_id FROM orders WHERE user_id=2 AND status='DELIVERED'  ORDER BY order_id DESC;
SELECT TOP 1 @o7 = order_id FROM orders WHERE user_id=2 AND status='CANCELLED'  ORDER BY order_id DESC;
SELECT TOP 1 @o8 = order_id FROM orders WHERE user_id=2 AND status='RETURNED'   ORDER BY order_id DESC;

INSERT INTO order_items (order_id, book_id, quantity, unit_price) VALUES
(@o1, 1, 1, 120.00),
(@o2, 1, 1, 120.00), (@o2, 2, 1,  85.00), (@o2, 3, 1,  50.00),
(@o3, 2, 1,  85.00),
(@o4, 3, 1, 150.00), (@o4, 5, 1,  50.00),
(@o5, 4, 1,  95.00), (@o5, 2, 1,  79.00),
(@o6, 3, 1, 150.00),
(@o7, 5, 1,  79.00),
(@o8, 4, 1,  95.00);
GO

PRINT N'=== DỮ LIỆU MẪU ĐƠN HÀNG ĐƯỢC THÊM THÀNH CÔNG! ===';

-- ============================================================
-- 3. CÁC CÂU LỆNH THAY ĐỔI TRẠNG THÁI ĐỂ TEST
-- Chạy từng lệnh để quan sát thay đổi trong giao diện lịch sử đơn hàng
-- ============================================================

-- Ví dụ: chuyển đơn NEW sang CONFIRMED
-- UPDATE orders SET status = 'CONFIRMED', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Chuyển CONFIRMED → PREPARING
-- UPDATE orders SET status = 'PREPARING', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Chuyển PREPARING → SHIPPING
-- UPDATE orders SET status = 'SHIPPING', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Chuyển SHIPPING → DELIVERING
-- UPDATE orders SET status = 'DELIVERING', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Chuyển DELIVERING → DELIVERED
-- UPDATE orders SET status = 'DELIVERED', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Hủy đơn bất kỳ
-- UPDATE orders SET status = 'CANCELLED', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Hoàn đơn
-- UPDATE orders SET status = 'RETURNED', updated_at = GETDATE()
--   WHERE order_id = <mã đơn>;

-- Xem tất cả đơn hàng của user 2
-- SELECT o.order_id, o.status, o.total_amount, o.created_at,
--        COUNT(oi.item_id) as so_sp
-- FROM orders o LEFT JOIN order_items oi ON o.order_id = oi.order_id
-- WHERE o.user_id = 2
-- GROUP BY o.order_id, o.status, o.total_amount, o.created_at
-- ORDER BY o.order_id;
