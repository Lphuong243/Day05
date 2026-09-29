-- Script khởi tạo Database PostgreSQL cho Day05
-- CREATE DATABASE murach_db;

DROP TABLE IF EXISTS "User" CASCADE;

CREATE TABLE "User" (
    "UserID" SERIAL PRIMARY KEY,
    "Email" VARCHAR(100) NOT NULL,
    "FirstName" VARCHAR(50) NOT NULL,
    "LastName" VARCHAR(50) NOT NULL
);

INSERT INTO "User" ("Email", "FirstName", "LastName") VALUES 
('jsmith@gmail.com', 'John', 'Smith'),
('andi@murach.com', 'Andrea', 'Steelman'),
('joelmurach@yahoo.com', 'Joel', 'Murach');

SELECT * FROM "User";
