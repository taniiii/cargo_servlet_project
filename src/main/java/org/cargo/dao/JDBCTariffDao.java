package org.cargo.dao;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCTariffDao implements TariffDao {

    private static final Logger LOGGER = Logger.getLogger(JDBCTariffDao.class);
    private Connection connection;

    public JDBCTariffDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates new tariff in database.
     * Saves new tariff id from database.
     */
    @Override
    public Tariff create(Object entity) {
        LOGGER.debug("Creating new tariff");
        Tariff tariff = (Tariff) entity;

        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO tariffs (price, address, size, weight, delivery_term_days) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, tariff.getPrice());
            pstm.setString(2, tariff.getAddress().name());
            pstm.setString(3, tariff.getSize().name());
            pstm.setString(4, tariff.getWeight().name());
            pstm.setInt(5, tariff.getDeliveryTermDays());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tariff.setId(generatedKeys.getInt(1));
                }
            }
            pstm.close();

            LOGGER.debug("Tariff has been added");
            return tariff;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e.getMessage()); //TODO castom exception
        }
    }

    /**
     * Searches user by user id.
     * @return fetches user from database
     */
    @Override
    public Tariff findById(int id) {
        LOGGER.debug("Getting tariff with id " + id);
        Tariff tariff = null;

        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tariffs WHERE id = ?")) {

            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            tariff = mapTariff(rs);
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage()); //TODO
        }
        return tariff;
    }
    /**
     * Fetches all of the existing tariffs from database.
     * @return List of tariffs
     */
    @Override
    public List<Tariff> findAll() {
        List<Tariff> tariffList = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tariffs");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                tariffList.add(mapTariff(rs));
            }
            rs.close();
            pstm.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage()); //TODO
        }
        return tariffList;
    }

    public List findPages(Integer offset, Integer size, String sortDirection){
        LOGGER.info("Getting page with offset " + offset + ", size " + size);
        List<Tariff> tariffs = new ArrayList<>();

        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tariffs ORDER BY address "
                + sortDirection.toUpperCase() + " LIMIT ?, ?")){

            pstm.setInt(1, offset);
            pstm.setInt(2, size);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                tariffs.add(mapTariff(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return tariffs;
    }

    /**
     * Fetches existing tariff from database with declared parameters of:
     * //     * @param  address - address enum name
     * //     * @param size - size enum name
     * //     * @param  weight - weight enum name
     */
//    public Tariff findTariff(String address, String size, String weight){
//        LOGGER.debug("Getting tariff by address = " + address + "and size = " + size + "and weight = " + weight);
//
//        Tariff tariff = null;
//        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tariffs WHERE address = ? AND size = ? AND weight = ?")) {
//            pstm.setString(1, address); //прверить нужен стринг или нєйм или обджект
//            pstm.setString(2,  size);
//            pstm.setString(3, weight);
//            ResultSet rs = pstm.executeQuery();
//            tariff = mapTariff(rs);
//            rs.close();
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//        }
//        return tariff;
//    }
    private Tariff mapTariff(ResultSet rs) throws SQLException {
        Tariff temp = new TariffBuilder().setId(rs.getInt("id"))
                .setPrice(rs.getInt("price"))
                .setAddress(Address.valueOf(rs.getString("address")))
                .setSize(Size.valueOf(rs.getString("size")))
                .setWeight(Weight.valueOf(rs.getString("weight")))
                .setDeliveryTermDays(rs.getInt("delivery_term_days"))
                .build();
        return temp;
    }

    @Override
    public boolean update(Object entity) {
        LOGGER.debug("Updating current tariff --> " + entity);
        Tariff tariff = (Tariff) entity;

        try {
            PreparedStatement pstm = connection.prepareStatement("UPDATE tariffs set price=?, address=?, size=?, weight=?, delivery_term_days=? WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, tariff.getPrice());
            pstm.setString(2, tariff.getAddress().name());
            pstm.setString(3, tariff.getSize().name());
            pstm.setString(4, tariff.getWeight().name());
            pstm.setInt(5, tariff.getDeliveryTermDays());

            pstm.executeUpdate();

            LOGGER.debug("Tariff id=" + tariff.getId() + " has been updated");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage()); //TODO
            return false;
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);  //TODO logger + work out
        }
    }
}
