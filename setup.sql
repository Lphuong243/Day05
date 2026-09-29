-- ========================================================
-- Script khởi tạo Database PostgreSQL cho ứng dụng Day05 SQL Gateway
-- ========================================================

-- 1. Nếu chưa có database, chạy lệnh sau trong pgAdmin hoặc psql:
-- CREATE DATABASE murach_db;

-- 2. Kết nối vào database murach_db và thực thi các lệnh bên dưới:

-- Lưu ý: Trong PostgreSQL, 'user' là từ khóa đặc biệt (reserved keyword),
-- do đó nên đặt tên bảng và cột trong dấu ngoặc kép "..." để trùng khớp chính xác
-- với câu lệnh trong sách Murach:
-- INSERT INTO "User" ("Email", "FirstName", "LastName") ...
-- SELECT * FROM "User";

DROP TABLE IF EXISTS "User" CASCADE;

CREATE TABLE "User" (
    "UserID" SERIAL PRIMARY KEY,
    "Email" VARCHAR(100) NOT NULL,
    "FirstName" VARCHAR(50) NOT NULL,
    "LastName" VARCHAR(50) NOT NULL
);

-- Thêm sẵn 3 dòng dữ liệu mẫu ban đầu (như hiển thị ở hình ảnh số 2)
INSERT INTO "User" ("Email", "FirstName", "LastName") VALUES 
('jsmith@gmail.com', 'John', 'Smith'),
('andi@murach.com', 'Andrea', 'Steelman'),
('joelmurach@yahoo.com', 'Joel', 'Murach');

-- Kiểm tra dữ liệu:
SELECT * FROM "User";
