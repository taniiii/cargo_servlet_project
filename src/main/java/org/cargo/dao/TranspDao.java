package org.cargo.dao;

import org.cargo.bean.transportation.OrderStatus;
import org.cargo.bean.transportation.Tariff;
import org.cargo.bean.transportation.Transportation;
import org.cargo.bean.user.User;

import java.time.LocalDate;
import java.util.List;

public interface TranspDao extends GenericDao{

    boolean saveStatus(Integer id, OrderStatus status);

    List<Transportation> findTransportationByUserId(Integer id, Integer offset, Integer size, String sortDirection, String sortBy);

    List<Transportation> findPages(Integer offset, Integer size, String sortDirection, String sortBy);

    List<Transportation> findTransportationByAddress(Integer offset, Integer size, String sortDirection, String address);

    List<Transportation> findTransportationByDate(Integer offset, Integer size, String sortDirection, String sortBy, LocalDate date);
}
