package org.cargo.service;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.dao.DaoFactory;
import org.cargo.dao.UserDao;
import org.cargo.exception.DaoException;

import java.util.List;

/**
 * This class is used to fetch information from DAO layer.
 *
 */
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static UserService userService;

    public static UserService getInstance(){
        if(userService == null){
            synchronized (UserService.class){
                if(userService == null){
                    UserService temp = new UserService();
                    userService = temp;
                }
            }
        }
        return userService;
    }

    /**
     * This method fetches all the users from database.
     */
    public List<User> getAllUsers() throws DaoException {
        LOGGER.debug("Fetching all the users from database");

        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        }
    }

    public Page<User> getAllUsersPaginated(Integer pageNo, Integer pageSize,
                                           String sortDirection, String sortBy) throws DaoException {
        LOGGER.debug("Fetching all the users from database paginated");

        try (UserDao userDao = daoFactory.createUserDao()) {
            List<User> items = userDao.findPages((pageNo - 1) * pageSize, pageSize, sortDirection, sortBy);
            return new Page<User>(items, pageNo, pageSize);
        }
    }

    /**
     * This method check weather email is already used.
     *
     * @param username username to check.
     * @param password is password to check
     * @return User from database found for these credentials.
     */

    public User getUserByCredentials(String username, String password) throws DaoException {
        LOGGER.debug("Getting user from database by credentials");

        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByUsernameAndPassword(username, password);
        }
    }

    public User registerUser(User user) throws DaoException {
        LOGGER.debug("New user registration");

        try (UserDao userDao = daoFactory.createUserDao()) {
            return (User) userDao.create(user);
        }
    }

    public User findUserByUsername(String username) throws DaoException {
        LOGGER.debug("Finding user by username " + username);

        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByUsername(username);
        }
    }

    public User findUserById(Integer id) throws DaoException {
        LOGGER.debug("Finding user by id " + id);

        try (UserDao userDao = daoFactory.createUserDao()) {
            return (User) userDao.findById(id);
        }
    }

    public boolean updateProfile(User user) throws DaoException {
        LOGGER.debug("Updating user from database");

        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.update(user);
        }
    }


    public boolean saveUser(User user, String updateRole) throws DaoException {
        LOGGER.debug("Saving new user authrities to database");

        user.setUserRole(Role.valueOf(updateRole));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.save(user);
        }
    }
}
