package org.cargo.dao;

import org.apache.log4j.Logger;
import org.cargo.service.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class JDBCDaoFactory extends DaoFactory{
     private static final Logger LOGGER = Logger.getLogger(JDBCDaoFactory.class);
     private DataSource dataSource = ConnectionPoolHolder.getDataSource();

     @Override
     public UserDao createUserDao(){
          LOGGER.debug("Creating new connection for JDBCUserDao");
          return new JDBCUserDao(getConnection());
     }

     @Override
     public TariffDao createTariffDao(){
          LOGGER.debug("Creating new connection for JDBCTariffDao");
          return new JDBCTariffDao(getConnection());
     }
     @Override
     public TranspDao createTranspDao() {
          LOGGER.debug("Creating new connection for JDBCTranspDao");
          return new JDBCTranspDao(getConnection());
     }

     private Connection getConnection(){
          LOGGER.debug("Getting new connection to database");
          try {
               return dataSource.getConnection();
          } catch (SQLException e) {
               LOGGER.error(e.getMessage());
               throw new RuntimeException();//TODO кстомное исключение
          }
     }
}
