package com.srms.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provides database connections.
 * Credentials are read from src/main/resources/db.properties (not committed to Git).
 */
public final class DBConnection {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                    "db.properties not found! Copy db.properties.example to db.properties "
                    + "(in src/main/resources) and add your MySQL details.");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read db.properties", e);
        }
    }

    private DBConnection() { }   // utility class - no objects

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password"));
    }
}
