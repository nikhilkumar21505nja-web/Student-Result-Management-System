package com.srms.model;

import com.srms.service.GradeCalculator;

/**
 * Marks of one student in one subject (joined view of marks + subjects).
 * It knows how to work out its own percentage, grade and pass/fail status.
 */
public class SubjectResult {

    private final int subjectId;
    private final String subjectCode;
    private final String subjectName;
    private final int maxMarks;
    private final double marksObtained;

    public SubjectResult(int subjectId, String subjectCode, String subjectName,
                         int maxMarks, double marksObtained) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.maxMarks = maxMarks;
        this.marksObtained = marksObtained;
    }

    public int getSubjectId() { return subjectId; }
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public int getMaxMarks() { return maxMarks; }
    public double getMarksObtained() { return marksObtained; }

    public double getPercentage() {
        return (marksObtained / maxMarks) * 100.0;
    }

    public String getGrade() {
        return GradeCalculator.getGrade(getPercentage());
    }

    public boolean isPassed() {
        return GradeCalculator.isPass(getPercentage());
    }
}
