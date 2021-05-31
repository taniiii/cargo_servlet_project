package org.cargo.dao;

import org.cargo.bean.transportation.Address;
import org.cargo.bean.transportation.Size;
import org.cargo.bean.transportation.Tariff;
import org.cargo.bean.transportation.Weight;

import java.util.List;

public interface TariffDao extends GenericDao{

    //Tariff findTariff(String address, String size, String weight);

    List<Tariff> findPages(Integer offset, Integer size, String sortDirection);
}
