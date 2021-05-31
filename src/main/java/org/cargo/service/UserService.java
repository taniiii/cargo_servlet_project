package org.cargo.service;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Tariff;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.dao.DaoFactory;
import org.cargo.dao.JDBCDaoFactory;
import org.cargo.dao.UserDao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public List<User> getAllUsers(){
        LOGGER.debug("Fetching all the users from database");

        try(UserDao userDao = daoFactory.createUserDao()){
            return userDao.findAll();
        }
    }

    public Page<User> getAllUsersPaginated(Integer pageNo, Integer pageSize, String sortDirection, String sortBy){
        LOGGER.debug("Fetching all the users from database paginated");

        try(UserDao userDao = daoFactory.createUserDao()){
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

    public User getUserByCredentials(String username, String password) {
        LOGGER.debug("Getting user from database by credentials");

        if (username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("email or password should not be empty/null"); //TODO на кастомное исключение
        } else {
            try (UserDao userDao = daoFactory.createUserDao()) {
                return userDao.findUserByUsernameAndPassword(username, password);
            } //TODO нужно ли закрывать close();
        }
    }

    public User registerUser(User user) {
        LOGGER.debug("New user registration");

            try(UserDao userDao = daoFactory.createUserDao()){
                return (User) userDao.create(user);
            }
    }

    public User findUserByUsername(String username) {
        LOGGER.debug("Finding user by username " + username);

        try(UserDao userDao = daoFactory.createUserDao()){
            return userDao.findUserByUsername(username);
        }

    }

    public User findUserById(Integer id) {
        LOGGER.debug("Finding user by id " + id);

        if (id == null) {
            throw new RuntimeException("Id should not be empty/null"); //TODO
        }
        try(UserDao userDao = daoFactory.createUserDao()){
            return (User) userDao.findById(id);
        }
    }

    public boolean updateProfile(User user) {
        LOGGER.debug("Updating user from database");

            try(UserDao userDao = daoFactory.createUserDao()){
                return userDao.update(user);
            }
    }


    public boolean saveUser(User user, String updateRole) {
        LOGGER.debug("Saving new user authrities to database");

        Set<String> roles = Arrays.stream(Role.values()) //смотрим какие роли есть вообще
                .map(Role::name)
                .collect(Collectors.toSet());

        if (roles.contains(updateRole)) {
            user.setUserRole(Role.valueOf(updateRole));
            try(UserDao userDao = daoFactory.createUserDao()) {
               return userDao.save(user);
            }
        }
        return false;
    }
}
