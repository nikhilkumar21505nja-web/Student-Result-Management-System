-- ============================================================
--  Student Result Management System - Database Schema
--  Run:  mysql -u root -p < database/schema.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS srms_db;
USE srms_db;

-- Drop in child-to-parent order so the script can be re-run safely
DROP TABLE IF EXISTS marks;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS students;

-- ------------------------------------------------------------
-- STUDENTS
-- ------------------------------------------------------------
CREATE TABLE students (
    student_id  INT          AUTO_INCREMENT PRIMARY KEY,
    roll_no     VARCHAR(20)  NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
    class_name  VARCHAR(50)  NOT NULL
);

-- ------------------------------------------------------------
-- SUBJECTS
-- ------------------------------------------------------------
CREATE TABLE subjects (
    subject_id    INT          AUTO_INCREMENT PRIMARY KEY,
    subject_code  VARCHAR(20)  NOT NULL UNIQUE,
    subject_name  VARCHAR(100) NOT NULL,
    max_marks     INT          NOT NULL DEFAULT 100 CHECK (max_marks > 0)
);

-- ------------------------------------------------------------
-- MARKS  (one row per student per subject)
-- ------------------------------------------------------------
CREATE TABLE marks (
    mark_id         INT           AUTO_INCREMENT PRIMARY KEY,
    student_id      INT           NOT NULL,
    subject_id      INT           NOT NULL,
    marks_obtained  DECIMAL(5,2)  NOT NULL CHECK (marks_obtained >= 0),

    CONSTRAINT fk_marks_student FOREIGN KEY (student_id)
        REFERENCES students(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_marks_subject FOREIGN KEY (subject_id)
        REFERENCES subjects(subject_id) ON DELETE CASCADE,
    CONSTRAINT uq_student_subject UNIQUE (student_id, subject_id)
);
