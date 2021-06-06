package org.cargo.dao;

import org.cargo.exception.DaoException;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    Object create(T entity) throws DaoException;

    T findById(int id) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean update(T entity) throws DaoException;

    void close() throws DaoException;
}
