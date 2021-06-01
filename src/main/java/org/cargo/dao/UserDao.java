package org.cargo.dao;

import org.cargo.bean.user.User;

import java.util.List;

public interface UserDao extends GenericDao{
    /**
     * This method searches user by credentials.
     */
    User findUserByUsernameAndPassword(String username, String password);
    /**
     * This method searches user by username.
     */
    User findUserByUsername(String username);

    boolean save(User user);

    List<User> findPages(Integer offset, Integer size, String sortDirection, String sortBy);
}
