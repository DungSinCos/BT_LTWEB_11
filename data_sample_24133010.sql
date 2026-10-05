-- ============================================================================
-- SCRIPT TAO DU LIEU MAU CHO HE THONG BOOKSTORE (MSSV: 24133010)
-- HO TRO TIENG VIET UNICODE CHUAN
-- Thuc thi script nay trong SQL Server Management Studio tren database BookStore
-- ============================================================================

USE BookStore;
GO

-- 1. CHUYEN CAC COT CHUOI SANG NVARCHAR DE HO TRO TIENG VIET CO DAU
ALTER TABLE author ALTER COLUMN author_name NVARCHAR(100);
ALTER TABLE books ALTER COLUMN publisher NVARCHAR(100);
ALTER TABLE books ALTER COLUMN title NVARCHAR(200);
ALTER TABLE books ALTER COLUMN description NVARCHAR(MAX);
ALTER TABLE users ALTER COLUMN fullname NVARCHAR(50);
ALTER TABLE rating ALTER COLUMN review_text NVARCHAR(MAX);
GO

-- 2. XOA DU LIEU CU
DELETE FROM rating;
DELETE FROM book_author;
DELETE FROM books;
DELETE FROM author;
DELETE FROM users;
GO

-- 3. THEM TAI KHOAN MAU (Password: 123456 -> MD5: e10adc3949ba59abbe56e057f20f883e)
SET IDENTITY_INSERT users ON;
INSERT INTO users (id, email, fullname, phone, passwd, signup_date, last_login, is_admin)
VALUES 
(1, 'admin@gmail.com', N'Quản Trị Viên (Admin)', '0901234567', 'e10adc3949ba59abbe56e057f20f883e', GETDATE(), GETDATE(), 1),
(2, 'dung@gmail.com', N'Lục Long Trí Dũng', '0912345678', 'e10adc3949ba59abbe56e057f20f883e', GETDATE(), GETDATE(), 0),
(3, 'user1@gmail.com', N'Nguyễn Văn A', '0922334455', 'e10adc3949ba59abbe56e057f20f883e', GETDATE(), GETDATE(), 0),
(4, 'user2@gmail.com', N'Trần Thị B', '0933445566', 'e10adc3949ba59abbe56e057f20f883e', GETDATE(), GETDATE(), 0);
SET IDENTITY_INSERT users OFF;
GO

-- 4. THEM TAC GIA MAU
SET IDENTITY_INSERT author ON;
INSERT INTO author (author_id, author_name, date_of_birth)
VALUES 
(1, N'J.K. Rowling', '1965-07-31'),
(2, N'Nguyễn Nhật Ánh', '1955-05-07'),
(3, N'George Orwell', '1903-06-25'),
(4, N'Dale Carnegie', '1888-11-24'),
(5, N'Paulo Coelho', '1947-08-24'),
(6, N'Tô Hoài', '1920-09-27');
SET IDENTITY_INSERT author OFF;
GO

-- 5. THEM SACH MAU (10 cuon sach de test phan trang 6 san pham / trang)
SET IDENTITY_INSERT books ON;
INSERT INTO books (bookid, isbn, title, publisher, price, description, publish_date, cover_image, quantity)
VALUES 
(1, '978-0747532699', N'Harry Potter và Hòn Đá Phù Thủy', N'NXB Trẻ', 120.00, N'Cuộc phiêu lưu kỳ thú của cậu bé phù thủy Harry Potter tại trường Hogwarts.', '1997-06-26', 'harry_potter.jpg', 50),
(2, '978-6041142512', N'Mắt Biếc', N'NXB Trẻ', 85.00, N'Tác phẩm truyện dài lãng mạn lôi cuốn của nhà văn Nguyễn Nhật Ánh.', '1990-01-01', 'mat_biec.jpg', 30),
(3, '978-0451524935', N'1984', N'Secker & Warburg', 150.00, N'Tiểu thuyết tiểu biểu về xã hội viễn tưởng và quyền lực tư tưởng.', '1949-06-08', '1984.jpg', 20),
(4, '978-0671027032', N'Đắc Nhân Tâm', N'NXB Tổng Hợp TPHCM', 95.00, N'Cuốn sách nghệ thuật thu phục lòng người hay nhất mọi thời đại.', '1936-10-01', 'dac_nhan_tam.jpg', 100),
(5, '978-0062315007', N'Nhà Giả Kim', N'NXB Hội Nhà Văn', 79.00, N'Hành trình theo đuổi vận mệnh của chàng chăn cừu Santiago.', '1988-01-01', 'nha_gia_kim.jpg', 45),
(6, '978-6041001234', N'Dế Mèn Phiêu Lưu Ký', N'NXB Kim Đồng', 45.00, N'Tác phẩm văn học thiếu nhi kinh điển của nhà văn Tô Hoài.', '1941-01-01', 'de_men.jpg', 80),
(7, '978-6041005678', N'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', N'NXB Trẻ', 110.00, N'Bức tranh tuổi thơ yên bình và xúc động tại vùng quê Việt Nam.', '2010-12-09', 'hoa_vang.jpg', 25),
(8, '978-0451526342', N'Trại Súc Vật (Animal Farm)', N'Secker & Warburg', 90.00, N'Tác phẩm trào phúng kinh điển về xã hội và bản chất con người.', '1945-08-17', 'animal_farm.jpg', 35),
(9, '978-6041009999', N'Cho Tôi Xin Một Vé Đi Tuổi Thơ', N'NXB Trẻ', 80.00, N'Vé tàu đưa người đọc trở về khoảng trời tuổi thơ trong sáng.', '2008-01-01', 'tuoi_tho.jpg', 60),
(10, '978-0060935467', N'Trại Trẻ Nhỡ Nhàng', N'NXB Văn Học', 135.00, N'Cuốn sách khám phá thế giới bí ẩn và lôi cuốn.', '2015-05-20', 'nhonhang.jpg', 15);
SET IDENTITY_INSERT books OFF;
GO

-- 6. KET NOI SACH VOI TAC GIA (book_author)
INSERT INTO book_author (bookid, author_id)
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 2),
(8, 3),
(9, 2),
(10, 5);
GO

-- 7. THEM RATINGS MAU (rating)
INSERT INTO rating (userid, bookid, rating, review_text)
VALUES 
(2, 1, 5, N'Sách rất hay, cốt truyện hấp dẫn và lôi cuốn từng trang!'),
(3, 1, 4, N'Sách đẹp, dịch thuật mượt mà.'),
(2, 2, 5, N'Truyện Nguyễn Nhật Ánh lúc nào cũng cảm xúc và hoài niệm.'),
(4, 2, 5, N'Đọc đi đọc lại vẫn thấy rất xúc động.'),
(3, 4, 5, N'Cuốn sách thay đổi tư duy giao tiếp rất nhiều, nên đọc!'),
(4, 5, 4, N'Ý nghĩa nhân văn sâu sắc về việc theo đuổi ước mơ.');
GO

PRINT N'=== THÊM DỮ LIỆU MẪU THÀNH CÔNG! ===';
