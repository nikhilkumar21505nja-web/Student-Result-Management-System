package com.srms.service;

/**
 * All grading rules live in this one class.
 * To change the grading scheme, edit only this file.
 *
 *   90% and above  -> A+      60% - 69%  -> B
 *   80% - 89%      -> A       50% - 59%  -> C
 *   70% - 79%      -> B+      40% - 49%  -> D
 *                             below 40%  -> F (Fail)
 */
public final class GradeCalculator {

    public static final double PASS_PERCENTAGE = 40.0;

    private GradeCalculator() { }

    public static String getGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B+";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        if (percentage >= PASS_PERCENTAGE) return "D";
        return "F";
    }

    public static boolean isPass(double percentage) {
        return percentage >= PASS_PERCENTAGE;
    }
}
