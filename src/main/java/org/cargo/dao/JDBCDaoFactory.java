package org.cargo.dao;

import org.apache.log4j.Logger;
import org.cargo.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class JDBCDaoFactory extends DaoFactory{
     private static final Logger LOGGER = Logger.getLogger(JDBCDaoFactory.class);
     private DataSource dataSource = ConnectionPoolHolder.getDataSource();

     @Override
     public UserDao createUserDao() throws DaoException {
          LOGGER.debug("Creating new connection for JDBCUserDao");
          return new JDBCUserDao(getConnection());
     }

     @Override
     public TariffDao createTariffDao() throws DaoException {
          LOGGER.debug("Creating new connection for JDBCTariffDao");
          return new JDBCTariffDao(getConnection());
     }

     @Override
     public TranspDao createTranspDao() throws DaoException {
          LOGGER.debug("Creating new connection for JDBCTranspDao");
          return new JDBCTranspDao(getConnection());
     }

     private Connection getConnection() throws DaoException {
          LOGGER.debug("Getting new connection to database");
          try {
               return dataSource.getConnection();
          } catch (SQLException e) {
               LOGGER.error(e.getMessage());
               throw new DaoException("Connection could not be closed");//new RuntimeException();//TODO кстомное исключение
          }
     }
}
