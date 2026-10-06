package com.srms.dao;

import com.srms.config.DBConnection;
import com.srms.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Database operations for the subjects table. */
public class SubjectDAO implements CrudDAO<Subject> {

    @Override
    public boolean add(Subject s) throws SQLException {
        String sql = "INSERT INTO subjects (subject_code, subject_name, max_marks) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getCode());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getMaxMarks());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Subject getById(int id) throws SQLException {
        return findOne("SELECT * FROM subjects WHERE subject_id = ?", String.valueOf(id));
    }

    public Subject getByCode(String code) throws SQLException {
        return findOne("SELECT * FROM subjects WHERE subject_code = ?", code);
    }

    @Override
    public List<Subject> getAll() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY subject_code";
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
    public boolean update(Subject s) throws SQLException {
        String sql = "UPDATE subjects SET subject_code = ?, subject_name = ?, max_marks = ? WHERE subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getCode());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getMaxMarks());
            ps.setInt(4, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM subjects WHERE subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------- helpers ----------

    private Subject findOne(String sql, String param) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    private Subject map(ResultSet rs) throws SQLException {
        Subject s = new Subject(
                rs.getString("subject_code"),
                rs.getString("subject_name"),
                rs.getInt("max_marks"));
        s.setId(rs.getInt("subject_id"));
        return s;
    }
}
