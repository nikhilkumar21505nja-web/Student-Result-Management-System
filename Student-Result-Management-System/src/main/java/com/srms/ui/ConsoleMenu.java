package com.srms.ui;

import com.srms.dao.MarkDAO;
import com.srms.dao.StudentDAO;
import com.srms.dao.SubjectDAO;
import com.srms.model.Student;
import com.srms.model.Subject;
import com.srms.model.SubjectResult;
import com.srms.service.ReportService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

/** Text-based menu that connects the user to the DAO and service classes. */
public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final MarkDAO markDAO = new MarkDAO();
    private final ReportService reportService = new ReportService();

    public void start() {
        System.out.println("\n  STUDENT RESULT MANAGEMENT SYSTEM");
        boolean running = true;
        while (running) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Subjects");
            System.out.println("3. Manage Marks");
            System.out.println("4. Generate Report Card");
            System.out.println("0. Exit");
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> studentMenu();
                    case 2 -> subjectMenu();
                    case 3 -> marksMenu();
                    case 4 -> reportCard();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (SQLException e) {
                printSqlError(e);
            }
        }
        System.out.println("Goodbye!");
    }

    // ================= STUDENTS =================

    private void studentMenu() throws SQLException {
        System.out.println("\n--- Students ---");
        System.out.println("1. Add  2. View all  3. Update  4. Delete  0. Back");
        switch (readInt("Enter choice: ")) {
            case 1 -> {
                String roll = readText("Roll No      : ");
                String name = readText("Name         : ");
                String email = readText("Email        : ");
                String cls = readText("Class        : ");
                studentDAO.add(new Student(roll, name, email, cls));
                System.out.println("Student added.");
            }
            case 2 -> {
                List<Student> list = studentDAO.getAll();
                if (list.isEmpty()) {
                    System.out.println("No students found.");
                    return;
                }
                System.out.printf("%-4s %-10s %-22s %-26s %s%n", "ID", "Roll No", "Name", "Email", "Class");
                list.forEach(System.out::println);
            }
            case 3 -> {
                Student s = askStudent();
                if (s == null) return;
                System.out.println("Press Enter to keep the current value.");
                s.setName(readOptional("Name  [" + s.getName() + "]: ", s.getName()));
                s.setEmail(readOptional("Email [" + s.getEmail() + "]: ", s.getEmail()));
                s.setClassName(readOptional("Class [" + s.getClassName() + "]: ", s.getClassName()));
                studentDAO.update(s);
                System.out.println("Student updated.");
            }
            case 4 -> {
                Student s = askStudent();
                if (s == null) return;
                if (confirm("Delete " + s.getName() + " and all their marks?")) {
                    studentDAO.delete(s.getId());
                    System.out.println("Student deleted.");
                }
            }
            default -> { }
        }
    }

    // ================= SUBJECTS =================

    private void subjectMenu() throws SQLException {
        System.out.println("\n--- Subjects ---");
        System.out.println("1. Add  2. View all  3. Update  4. Delete  0. Back");
        switch (readInt("Enter choice: ")) {
            case 1 -> {
                String code = readText("Subject Code : ");
                String name = readText("Subject Name : ");
                int max = readInt("Max Marks    : ");
                if (max <= 0) {
                    System.out.println("Max marks must be greater than 0.");
                    return;
                }
                subjectDAO.add(new Subject(code, name, max));
                System.out.println("Subject added.");
            }
            case 2 -> {
                List<Subject> list = subjectDAO.getAll();
                if (list.isEmpty()) {
                    System.out.println("No subjects found.");
                    return;
                }
                System.out.printf("%-4s %-10s %-28s %s%n", "ID", "Code", "Name", "Max");
                list.forEach(System.out::println);
            }
            case 3 -> {
                Subject s = askSubject();
                if (s == null) return;
                System.out.println("Press Enter to keep the current value.");
                s.setName(readOptional("Name [" + s.getName() + "]: ", s.getName()));
                String max = readOptional("Max marks [" + s.getMaxMarks() + "]: ", String.valueOf(s.getMaxMarks()));
                try {
                    s.setMaxMarks(Integer.parseInt(max));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number, keeping old max marks.");
                }
                subjectDAO.update(s);
                System.out.println("Subject updated.");
            }
            case 4 -> {
                Subject s = askSubject();
                if (s == null) return;
                if (confirm("Delete " + s.getName() + " and all marks entered for it?")) {
                    subjectDAO.delete(s.getId());
                    System.out.println("Subject deleted.");
                }
            }
            default -> { }
        }
    }

    // ================= MARKS =================

    private void marksMenu() throws SQLException {
        System.out.println("\n--- Marks ---");
        System.out.println("1. Add marks  2. View student's marks  3. Update marks  4. Delete marks  0. Back");
        int choice = readInt("Enter choice: ");
        if (choice < 1 || choice > 4) return;

        Student student = askStudent();
        if (student == null) return;

        if (choice == 2) {
            List<SubjectResult> list = markDAO.getByStudent(student.getId());
            if (list.isEmpty()) {
                System.out.println("No marks entered yet.");
                return;
            }
            System.out.printf("%-10s %-28s %s%n", "Code", "Subject", "Marks");
            for (SubjectResult r : list) {
                System.out.printf("%-10s %-28s %.1f / %d%n",
                        r.getSubjectCode(), r.getSubjectName(), r.getMarksObtained(), r.getMaxMarks());
            }
            return;
        }

        Subject subject = askSubject();
        if (subject == null) return;

        switch (choice) {
            case 1, 3 -> {
                double marks = readDouble("Marks (0 - " + subject.getMaxMarks() + "): ");
                if (marks < 0 || marks > subject.getMaxMarks()) {
                    System.out.println("Marks must be between 0 and " + subject.getMaxMarks() + ".");
                    return;
                }
                boolean done = (choice == 1)
                        ? markDAO.add(student.getId(), subject.getId(), marks)
                        : markDAO.update(student.getId(), subject.getId(), marks);
                System.out.println(done ? "Marks saved." : "No record found to update. Add marks first.");
            }
            case 4 -> {
                boolean done = markDAO.delete(student.getId(), subject.getId());
                System.out.println(done ? "Marks deleted." : "No marks found for this subject.");
            }
            default -> { }
        }
    }

    // ================= REPORT CARD =================

    private void reportCard() throws SQLException {
        Student student = askStudent();
        if (student == null) return;
        reportService.printReportCard(student, markDAO.getByStudent(student.getId()));
    }

    // ================= INPUT HELPERS =================

    private Student askStudent() throws SQLException {
        String roll = readText("Student Roll No: ");
        Student s = studentDAO.getByRollNo(roll);
        if (s == null) System.out.println("No student with roll no '" + roll + "'.");
        return s;
    }

    private Subject askSubject() throws SQLException {
        String code = readText("Subject Code: ");
        Subject s = subjectDAO.getByCode(code);
        if (s == null) System.out.println("No subject with code '" + code + "'.");
        return s;
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readOptional(String prompt, String current) {
        String input = readText(prompt);
        return input.isEmpty() ? current : input;
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private boolean confirm(String question) {
        return readText(question + " (y/n): ").equalsIgnoreCase("y");
    }

    private void printSqlError(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            System.out.println("Error: duplicate or invalid data (e.g. roll no / subject code / marks already exist).");
        } else {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
