-- ============================================================
--  Optional sample data so you can try the app immediately
--  Run:  mysql -u root -p srms_db < database/sample_data.sql
-- ============================================================
USE srms_db;

INSERT INTO students (roll_no, name, email, class_name) VALUES
('CS101', 'Aarav Sharma', 'aarav@example.com', 'BTech CSE - Sem 1'),
('CS102', 'Diya Patel',   'diya@example.com',  'BTech CSE - Sem 1'),
('CS103', 'Rohan Das',    'rohan@example.com', 'BTech CSE - Sem 1');

INSERT INTO subjects (subject_code, subject_name, max_marks) VALUES
('MATH101', 'Engineering Mathematics', 100),
('PHY101',  'Physics',                 100),
('PRG101',  'Programming in Java',     100),
('DBM101',  'Database Management',     100),
('ENG101',  'Communication English',   100);

-- Aarav: strong student
INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES
(1, 1, 92), (1, 2, 85), (1, 3, 96), (1, 4, 88), (1, 5, 79);

-- Diya: average student
INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES
(2, 1, 68), (2, 2, 72), (2, 3, 81), (2, 4, 64), (2, 5, 70);

-- Rohan: fails Physics
INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES
(3, 1, 55), (3, 2, 32), (3, 3, 61), (3, 4, 48), (3, 5, 58);
