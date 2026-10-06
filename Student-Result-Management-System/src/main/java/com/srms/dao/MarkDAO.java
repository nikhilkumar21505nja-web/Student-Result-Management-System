package com.srms.dao;

import com.srms.config.DBConnection;
import com.srms.model.SubjectResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Database operations for the marks table. */
public class MarkDAO {

    /** Create: add marks of a student for a subject. */
    public boolean add(int studentId, int subjectId, double marks) throws SQLException {
        String sql = "INSERT INTO marks (student_id, subject_id, marks_obtained) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setDouble(3, marks);
            return ps.executeUpdate() > 0;
        }
    }

    /** Update: change existing marks. */
    public boolean update(int studentId, int subjectId, double marks) throws SQLException {
        String sql = "UPDATE marks SET marks_obtained = ? WHERE student_id = ? AND subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, marks);
            ps.setInt(2, studentId);
            ps.setInt(3, subjectId);
            return ps.executeUpdate() > 0;
        }
    }

    /** Delete: remove marks of a student for one subject. */
    public boolean delete(int studentId, int subjectId) throws SQLException {
        String sql = "DELETE FROM marks WHERE student_id = ? AND subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            return ps.executeUpdate() > 0;
        }
    }

    /** Read: all subject results of one student (JOIN of marks and subjects). */
    public List<SubjectResult> getByStudent(int studentId) throws SQLException {
        List<SubjectResult> list = new ArrayList<>();
        String sql = "SELECT s.subject_id, s.subject_code, s.subject_name, s.max_marks, m.marks_obtained "
                   + "FROM marks m JOIN subjects s ON m.subject_id = s.subject_id "
                   + "WHERE m.student_id = ? ORDER BY s.subject_code";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SubjectResult(
                            rs.getInt("subject_id"),
                            rs.getString("subject_code"),
                            rs.getString("subject_name"),
                            rs.getInt("max_marks"),
                            rs.getDouble("marks_obtained")));
                }
            }
        }
        return list;
    }
}
