package org.cargo.dao;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;
import org.cargo.exception.DaoException;
import org.cargo.service.Encoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDao.class);
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates new user in database.
     * Saves new user id from database.
     */
    @Override
    public User create(Object entity) throws DaoException {
        LOGGER.debug("Creating new user");
        User user = (User) entity;

        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO users (username, email, password, role_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, Encoder.encrypt(user.getPassword()));
            pstm.setInt(4, user.getUserRole().ordinal() + 1);

            pstm.executeUpdate();

                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            pstm.close();

            LOGGER.debug("User has been added");
            return user;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not registered"); //TODO castom exception
        }
    }

    /**
     * Searches user by user id.
     *
     * @return fetches user from database
     */
    @Override
    public User findById(int id) throws DaoException {
        LOGGER.debug("Getting user with id " + id);
        User user = null;

        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            user = getUser(rs);
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not found");
        }

        return user;
    }

    /**
     * Fetches all the users from database.
     *
     * @return List of users
     */
    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                User user = new UserBuilder().setId(rs.getInt("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setEmail(rs.getString("email"))
                        .setUserRole(Role.values()[rs.getInt("role_id") - 1])
                            .build();

                userList.add(user);
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Users could not be found");
        }
        return userList;
    }

    @Override
    public List<User> findPages(Integer offset, Integer size, String sortDirection, String sortBy) throws DaoException {
        LOGGER.info("Getting page with offset " + offset + ", size " + size);
        List<User> users = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users ORDER BY " +
                sortBy + " " + sortDirection + " LIMIT ?, ?")) {

            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                User user = new UserBuilder().setId(rs.getInt("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setEmail(rs.getString("email"))
                        .setUserRole(Role.values()[rs.getInt("role_id") - 1])
                        .build();

                users.add(user);
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Users could not be found");
        }
        return users;
    }

    /**
     * Updates current user.
     *
     * @param entity represents current user
     */
    @Override
    public boolean update(Object entity) throws DaoException {
        LOGGER.debug("Updating current user --> " + entity);
        User user = (User) entity;

        try {
            PreparedStatement pstm = connection.prepareStatement("UPDATE users set email=?, password=? WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getEmail());
            pstm.setString(2, Encoder.encrypt(user.getPassword()));
            pstm.setInt(3, user.getId());

            pstm.executeUpdate();

            LOGGER.debug("User " + user.getUsername() + " has been updated");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not updated");
        }
    }

    /**
     * Searches user by user name.
     *
     * @param username represents username from DB     *
     */
    public User findUserByUsername(String username) throws DaoException {
        User user = null;

        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            pstm.setString(1, username);

            ResultSet rs = pstm.executeQuery();

            user = getUser(rs);
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not found by username");
        }
        return user;
    }

    /**
     * Saves new user's authorities: user name and user role.
     *
     * @return
     */
    @Override
    public boolean save(User user) throws DaoException {
        LOGGER.debug("Updating current user authorities--> " + user);
        User userToUpdate = user;

        try {
            PreparedStatement pstm = connection.prepareStatement("UPDATE users set username=?, role_id=? WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getUsername());
            pstm.setInt(2, user.getUserRole().ordinal() + 1);
            pstm.setInt(3, user.getId());

            pstm.executeUpdate();

            LOGGER.debug("User " + user.getUsername() + " has been saved");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not saved");
        }
    }

    /**
     * Searches user by credentials.
     *
     * @param username represents username from DB
     * @param password represents encrypted password from DB
     */
    public User findUserByUsernameAndPassword(String username, String password) throws DaoException {
        LOGGER.debug("Getting user with username = " + username + "and password = *");
        User user = null;
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pstm.setString(1, username);
            pstm.setString(2, Encoder.encrypt(password));
            ResultSet rs = pstm.executeQuery();
            user = getUser(rs);
            pstm.close();
            rs.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User was not found");
        }

        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = null;

            if (resultSet.next()) {
                user = new UserBuilder().setId(resultSet.getInt("id"))
                        .setUsername(resultSet.getString("username"))
                        .setPassword(resultSet.getString("password"))
                        .setEmail(resultSet.getString("email"))
                        .setUserRole(Role.values()[resultSet.getInt("role_id") - 1])
                        .build();
            }
        return user;
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Connection could not be closed");
        }
    }
}
