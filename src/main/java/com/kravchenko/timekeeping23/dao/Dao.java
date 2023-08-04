package com.kravchenko.timekeeping23.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Dao<K, T>{

    List<T> findAll(Connection connection);

    Optional<T> findById(Connection connection, K id);

    boolean delete(Connection connection, K id);

    T save(Connection connection, T entity);

    void update(Connection connection,T entity);
}
