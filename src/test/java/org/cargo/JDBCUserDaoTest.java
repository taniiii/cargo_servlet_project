package org.cargo;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;
import org.cargo.dao.JDBCUserDao;
import org.cargo.exception.DaoException;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCUserDaoTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cargo_servlet_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";

    Connection con;
    JDBCUserDao userDao;

    @BeforeClass
    public static void dbCreate() throws SQLException, IOException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/whole_db_test_create.sql"));
        sr.runScript(reader);
        reader.close();
    }

    @AfterClass
    public static void dropDown() throws SQLException {
        Connection conn = DBManager.getDataSource().getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP database cargo_servlet_test;");
    }

    @Before
    public void userDaoCreate() throws SQLException {
        con = DBManager.getDataSource().getConnection();
        userDao = new JDBCUserDao(con);
    }

    @Test
    public void shouldCreateNewUser() throws DaoException {

        User newUser = new UserBuilder().setUsername("newUser")
                .setEmail("user@gmail.com")
                .setPassword("user12345")
                .setUserRole(Role.USER).build();
        Assert.assertNotNull(userDao.create(newUser));
    }

    @Test
    public void shouldFindUserById() throws DaoException {
        User user = userDao.create(new UserBuilder().setUsername("user")
                .setEmail("user@gmail.com")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());

        Assert.assertNotNull(userDao.findById(user.getId()));
    }

    @Test
    public void shouldFindUserByUsername() throws DaoException {
        User user = userDao.create(new UserBuilder().setUsername("user11")
                .setEmail("user11@gmail.com")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());

        Assert.assertNotNull(userDao.findUserByUsername(user.getUsername()));
    }

    @Test
    public void shouldReturnUserList() throws DaoException {
        userDao.create(new UserBuilder().setUsername("user13")
                .setEmail("user13@gmail.com")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        userDao.create(new UserBuilder().setUsername("user12")
                .setEmail("user12@gmail.com")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());

        Assert.assertEquals(5, userDao.findAll().size());
    }
}
