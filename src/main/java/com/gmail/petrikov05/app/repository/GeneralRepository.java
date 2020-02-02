package com.gmail.petrikov05.app.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    List<T> findAll(Connection connection) throws SQLException;

    T add(Connection connection, T t) throws SQLException;

    Integer delete(Connection connection, Serializable id) throws SQLException;

}
