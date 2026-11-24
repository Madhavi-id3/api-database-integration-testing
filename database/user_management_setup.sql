-- ==========================================
-- API DATABASE INTEGRATION TESTING PROJECT
-- Database Setup Script
-- ==========================================

CREATE DATABASE IF NOT EXISTS user_management_db;

USE user_management_db;

-- Verify selected database
SELECT DATABASE();

-- Display available tables
SHOW TABLES;

-- Display users table structure
DESCRIBE users;

-- Display user records
SELECT * FROM users;