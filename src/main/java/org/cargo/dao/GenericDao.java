package org.cargo.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    Object create(T entity);

    T findById(int id);

    List<T> findAll();

    boolean update(T entity);

    void close();
}
