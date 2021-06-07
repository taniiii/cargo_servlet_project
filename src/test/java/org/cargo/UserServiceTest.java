package org.cargo;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.cargo.dao.DaoFactory;
import org.cargo.dao.JDBCUserDao;
import org.cargo.service.UserService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserServiceTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cargo_servlet_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";

    @InjectMocks
    UserService userService;

    @Mock
    JDBCUserDao jdbcUserDao;

    @Mock
    Connection con;

    @Mock
    DaoFactory daoFactory;

    @BeforeClass
    public static void dbCreate() throws SQLException, IOException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/whole_db_test_create.sql"));
        sr.runScript(reader);
        reader.close();
    }

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        con = DBManager.getDataSource().getConnection();
        jdbcUserDao = new JDBCUserDao(con);

    }

//    @Test
//    public void shouldCreateNewUser() throws DaoException {
//        when(daoFactory.createUserDao()).thenReturn(jdbcUserDao);
//
//        userService = UserService.getInstance();
//        User newUser = new UserBuilder().setUsername("newUser")
//                .setEmail("user@gmail.com")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build();
//        Assert.assertNotNull(userService.registerUser(newUser));
//    }

    @AfterClass
    public static void dropDown() throws SQLException {
        Connection conn = DBManager.getDataSource().getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP database cargo_servlet_test;");
    }
}
