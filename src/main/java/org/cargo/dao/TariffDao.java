package org.cargo.dao;

import org.cargo.bean.transportation.Tariff;

import java.util.List;

public interface TariffDao extends GenericDao{

    List<Tariff> findPages(Integer offset, Integer size, String sortDirection);
}
