package com.srms.service;

import com.srms.model.Student;
import com.srms.model.SubjectResult;

import java.util.List;

/** Builds and prints the term report card of a student. */
public class ReportService {

    private static final String LINE  = "=".repeat(72);
    private static final String THIN  = "-".repeat(72);

    public void printReportCard(Student student, List<SubjectResult> results) {
        System.out.println();
        System.out.println(LINE);
        System.out.println(center("STUDENT REPORT CARD", 72));
        System.out.println(LINE);
        System.out.printf("Name   : %-30s Roll No : %s%n", student.getName(), student.getRollNo());
        System.out.printf("Class  : %s%n", student.getClassName());
        System.out.println(THIN);

        if (results.isEmpty()) {
            System.out.println("No marks have been entered for this student yet.");
            System.out.println(LINE);
            return;
        }

        System.out.printf("%-10s %-28s %8s %8s %8s %6s%n",
                "Code", "Subject", "Max", "Marks", "%", "Grade");
        System.out.println(THIN);

        double totalObtained = 0;
        double totalMax = 0;
        boolean allPassed = true;

        for (SubjectResult r : results) {
            System.out.printf("%-10s %-28s %8d %8.1f %7.1f%% %6s%n",
                    r.getSubjectCode(), r.getSubjectName(), r.getMaxMarks(),
                    r.getMarksObtained(), r.getPercentage(), r.getGrade());
            totalObtained += r.getMarksObtained();
            totalMax += r.getMaxMarks();
            if (!r.isPassed()) {
                allPassed = false;
            }
        }

        double overallPercentage = (totalObtained / totalMax) * 100.0;
        String overallGrade = GradeCalculator.getGrade(overallPercentage);

        System.out.println(THIN);
        System.out.printf("Total Marks   : %.1f / %.0f%n", totalObtained, totalMax);
        System.out.printf("Percentage    : %.2f%%%n", overallPercentage);
        System.out.printf("Overall Grade : %s%n", allPassed ? overallGrade : "F");
        System.out.printf("Result        : %s%n", allPassed ? "PASS" : "FAIL (failed in one or more subjects)");
        System.out.println(LINE);
    }

    private String center(String text, int width) {
        int pad = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(pad) + text;
    }
}
