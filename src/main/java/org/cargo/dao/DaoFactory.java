package org.cargo.dao;

import org.cargo.exception.DaoException;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao() throws DaoException;

    public abstract TariffDao createTariffDao() throws DaoException;

    public abstract TranspDao createTranspDao() throws DaoException;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
