package org.cargo;


import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class DBManager {

    private static final ResourceBundle rb = ResourceBundle.getBundle("cargo_servlet_test");

    public static DataSource getDataSource() {
        MysqlDataSource mds = new MysqlDataSource();
        mds.setURL(rb.getString("url"));
        mds.setDatabaseName(rb.getString("name"));
        mds.setUser(rb.getString("user"));
        mds.setPassword(rb.getString("password"));
        return mds;
    }
}
