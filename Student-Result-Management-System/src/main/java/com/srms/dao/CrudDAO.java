package com.srms.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic CRUD contract (abstraction).
 * Every DAO that manages one entity type implements this interface.
 *
 * @param <T> the entity type (Student, Subject, ...)
 */
public interface CrudDAO<T> {

    boolean add(T item) throws SQLException;          // Create

    T getById(int id) throws SQLException;            // Read one

    List<T> getAll() throws SQLException;             // Read all

    boolean update(T item) throws SQLException;       // Update

    boolean delete(int id) throws SQLException;       // Delete
}
