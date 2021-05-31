package org.cargo.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import org.apache.commons.dbcp.*;                  for BasicDataSource

public class ConnectionPoolHolder {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder(){
        LOGGER.debug("Initializing ConnectionPoolHolder.class");
    }

    public static DataSource getDataSource(){
        if(dataSource == null){
            synchronized (ConnectionPoolHolder.class){
                if(dataSource == null){
                    try {
                        Context ctxt = new InitialContext();
                        dataSource = (DataSource) ctxt.lookup("java:comp/env/jdbc/cargo");
                    } catch (NamingException e) {
                        LOGGER.error(e.getMessage());
                        e.printStackTrace();
                    }

                }
            }
        }
        return dataSource;
    }
}
