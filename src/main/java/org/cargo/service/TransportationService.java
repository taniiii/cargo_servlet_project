package org.cargo.service;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.*;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.dao.DaoFactory;
import org.cargo.dao.TranspDao;
import org.cargo.dao.UserDao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransportationService {
    private static final Logger LOGGER = Logger.getLogger(TransportationService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static TransportationService trService;


    public static TransportationService getInstance(){
        if(trService == null){
            synchronized (TransportationService.class){
                if(trService == null){
                    TransportationService temp = new TransportationService();
                    trService = temp;
                }
            }
        }
        return trService;
    }
    /**
     * This method creates new transportation order with user,
     * tariff and order comment
     *
     */
    public Transportation createTransportation(User user, Tariff tariff, String comment) {//TODO testing services
        LOGGER.debug("Saving new transportation order");
//TODO check is comment is null then set "" or else set comment
      //  if(user != null && tariff != null){  //usr not null annotation
            Transportation temp = new TransportationBuilder().setComment(comment)
                                .setCustomer(user)
                                .setCreationDate(LocalDate.now())
                                .setTariff(tariff)
                                .setDeliveryDate(LocalDate.now().plusDays(tariff.getDeliveryTermDays()))
                                .setOrderStatus(OrderStatus.NEW)
                                .build();


            try(TranspDao transpDao = daoFactory.createTranspDao()){
                return (Transportation) transpDao.create(temp);
            }
        //}
    }

    /**
     * This method finds all existing transportations.
     */
    public List<Transportation> getAllTransportations(){
        LOGGER.debug("Fetching all the transportations from database");

        try(TranspDao transpDao = daoFactory.createTranspDao()){//;
//            UserDao userDao = daoFactory.createUserDao()){

            return transpDao.findAll();
        }
    }

    /**
     * This method finds unique transportation by its id.
     */
//    public Transportation findTransportationById(Integer id){
//        LOGGER.debug("Finding transportation by id " + id);
//
//        if (id == null) {
//            throw new RuntimeException("Id should not be empty/null"); //TODO
//        }
//        try(TranspDao transpDao = daoFactory.createTranspDao()){
//            return (Transportation) transpDao.findById(id);
//        }
//    }

    public boolean saveTransportation(Integer id, OrderStatus status){
        LOGGER.debug("Saving new transportation status to database");

            try(TranspDao transpDao = daoFactory.createTranspDao()) {
                return transpDao.saveStatus(id, status);
            }
        }


    public Page<Transportation> findTransportationsByUser(Object user, Integer pageNo, Integer pageSize, String sortDirection, String sortBy){
        LOGGER.debug("Fetching all user's transportations from database");

        User loggedUser = (User) user;
        try(TranspDao transpDao = daoFactory.createTranspDao()){
            List<Transportation> items = transpDao.findTransportationByUserId(
                    loggedUser.getId(), (pageNo - 1) * pageSize, pageSize, sortDirection, sortBy);
            return new Page<Transportation>(items, pageNo, pageSize);
        }
    }

    public Page<Transportation> getAllTransportationsPaginated(Integer pageNo, Integer pageSize, String sortDirection, String sortBy){
        LOGGER.debug("Fetching all the transportations from database paginated");

        try(TranspDao transpDao = daoFactory.createTranspDao()){
            List<Transportation> items = transpDao.findPages((pageNo - 1) * pageSize, pageSize, sortDirection, sortBy);
            return new Page<Transportation>(items, pageNo, pageSize);
        }
    }

    public Page<Transportation> findTransportationsByAddress(Integer pageNo, Integer pageSize, String sortDirection, String address){
        LOGGER.debug("Searching transportations from database by destination address");

        try(TranspDao transpDao = daoFactory.createTranspDao()){
            List<Transportation> items = transpDao.findTransportationByAddress((pageNo - 1) * pageSize, pageSize, sortDirection, address);
            return new Page<Transportation>(items, pageNo, pageSize);
        }
    }

    public Page<Transportation> findTransportationByDate(Integer pageNo, Integer pageSize, String sortBy, String sortDirection, LocalDate date){
        LOGGER.debug("Searching transportations from database by creation date");

        try(TranspDao transpDao = daoFactory.createTranspDao()){
            List<Transportation> items = transpDao.findTransportationByDate((pageNo - 1) * pageSize, pageSize, sortDirection, sortBy, date);
            return new Page<Transportation>(items, pageNo, pageSize);
        }
    }
}
