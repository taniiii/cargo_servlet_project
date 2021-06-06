package org.cargo.dao;

import org.cargo.bean.transportation.OrderStatus;
import org.cargo.bean.transportation.Transportation;
import org.cargo.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface TranspDao extends GenericDao{

    boolean saveStatus(Integer id, OrderStatus status) throws DaoException;

    List<Transportation> findTransportationByUserId(Integer id, Integer offset, Integer size, String sortDirection, String sortBy) throws DaoException;

    List<Transportation> findPages(Integer offset, Integer size, String sortDirection, String sortBy) throws DaoException;

    List<Transportation> findTransportationByAddress(Integer offset, Integer size, String sortDirection, String address) throws DaoException;

    List<Transportation> findTransportationByDate(Integer offset, Integer size, String sortDirection, String sortBy, LocalDate date) throws DaoException;
}
