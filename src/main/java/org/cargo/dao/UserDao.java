package org.cargo.dao;

import org.cargo.bean.user.User;
import org.cargo.exception.DaoException;

import java.util.List;

public interface UserDao extends GenericDao{
    /**
     * This method searches user by credentials.
     */
    User findUserByUsernameAndPassword(String username, String password) throws DaoException;

    /**
     * This method searches user by username.
     */
    User findUserByUsername(String username) throws DaoException;

    boolean save(User user) throws DaoException;

    List<User> findPages(Integer offset, Integer size, String sortDirection, String sortBy) throws DaoException;
}
