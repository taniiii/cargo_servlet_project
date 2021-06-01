package org.cargo.service;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Tariff;
import org.cargo.dao.DaoFactory;
import org.cargo.dao.TariffDao;

import java.util.List;

public class TariffService {
    private static final Logger LOGGER = Logger.getLogger(TariffService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static TariffService tariffService;

    public static TariffService getInstance(){
        if(tariffService == null){
            synchronized (TariffService.class){
                if(tariffService == null){
                    TariffService temp = new TariffService();
                    tariffService = temp;
                }
            }
        }
        return tariffService;
    }

    /**
     * This method fetches all the tariffs from database.
     *
     *
     */
    public List<Tariff> getAllTariffs(){
        try(TariffDao tariffDao = daoFactory.createTariffDao()){
            return tariffDao.findAll();
        }
    }

    public Page<Tariff> getAllTariffsPaginated(Integer pageNo, Integer pageSize, String sortDirection){
        LOGGER.info("Getting page number " + pageNo + ", of size " + pageSize);

        try(TariffDao tariffDao = daoFactory.createTariffDao()){
            List<Tariff> items = tariffDao.findPages((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Tariff>(items, pageNo, pageSize);
        }
    }
}
