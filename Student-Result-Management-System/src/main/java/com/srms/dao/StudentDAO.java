package com.srms.dao;

import com.srms.config.DBConnection;
import com.srms.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Database operations for the students table. */
public class StudentDAO implements CrudDAO<Student> {

    @Override
    public boolean add(Student s) throws SQLException {
        String sql = "INSERT INTO students (roll_no, name, email, class_name) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getClassName());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Student getById(int id) throws SQLException {
        return findOne("SELECT * FROM students WHERE student_id = ?", String.valueOf(id));
    }

    /** Handy for the console UI: students are usually looked up by roll number. */
    public Student getByRollNo(String rollNo) throws SQLException {
        return findOne("SELECT * FROM students WHERE roll_no = ?", rollNo);
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY roll_no";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public boolean update(Student s) throws SQLException {
        String sql = "UPDATE students SET roll_no = ?, name = ?, email = ?, class_name = ? WHERE student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getClassName());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";   // marks are removed by ON DELETE CASCADE
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------- helpers ----------

    private Student findOne(String sql, String param) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        Student s = new Student(
                rs.getString("roll_no"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("class_name"));
        s.setId(rs.getInt("student_id"));
        return s;
    }
}
