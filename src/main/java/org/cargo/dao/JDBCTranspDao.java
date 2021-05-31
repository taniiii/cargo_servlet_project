package org.cargo.dao;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.*;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCTranspDao implements TranspDao{
    private static final Logger LOGGER = Logger.getLogger(JDBCTranspDao.class);
    private Connection connection;

    public JDBCTranspDao(Connection connection){
        this.connection = connection;
    }

    /**
     *Creates new transportation in database.
     * Saves new transportation id from database.
     */
    @Override
    public Transportation create(Object entity) {
        LOGGER.debug("Creating new transportation");
        Transportation tr = (Transportation) entity;

        try(PreparedStatement pstm = connection.prepareStatement("INSERT INTO transportations (comment, user_id, tariff_id, created_at, delivery_at, status_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstm2 = connection.prepareStatement("SELECT * FROM tariffs WHERE address = ? AND size = ? AND weight = ?")) {
            connection.setAutoCommit(false);

            pstm2.setString(1, tr.getTariff().getAddress().name()); //прверить нужен стринг или нєйм или обджект
            pstm2.setString(2,  tr.getTariff().getSize().name());
            pstm2.setString(3, tr.getTariff().getWeight().name());
            ResultSet rs = pstm2.executeQuery();

            tr.setTariff(mapTariff(rs));
            rs.close();

            pstm.setString(1, tr.getComment());
            pstm.setInt(2, tr.getCustomer().getId());
            pstm.setInt(3, tr.getTariff().getId());
            pstm.setDate(4, Date.valueOf(tr.getCreationDate()));
            pstm.setDate(5, Date.valueOf(tr.getDeliveryDate()));
            pstm.setInt(6, OrderStatus.NEW.ordinal() + 1);

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tr.setId(generatedKeys.getInt(1));
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.debug("Transportation has been added");
            return tr;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     *Fetches transportation from database by id.
     *
     */
    @Override
    public Transportation findById(int id) {
        LOGGER.debug("Getting transportation with id " + id);
        Transportation tr = new Transportation();

        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM transportations WHERE id = ?;")){

            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();

            while (rst.next()) {
                tr = getTransportation(rst);
            }
            rst.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return tr;
    }

    /**
     * Fetches all the transportations from database.
     * @return List of transportations
     */
    @Override
    public List<Transportation> findAll() {
        LOGGER.debug("Creating a list of transportations");

        List<Transportation> transpList = new ArrayList<>();
        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM transportations")) {
            connection.setAutoCommit(false);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                transpList.add(getTransportation(rs));
            }
//            rs.close();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
        }
        return transpList;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }
    /**
     * Fetches single transportations from result set.
     * @return List of transportations
     */
    private Transportation getTransportation(ResultSet rs) throws SQLException {
        Transportation tr = new TransportationBuilder().setId(rs.getInt("id"))
                    .setComment(rs.getString("comment"))
                    .setCreationDate(rs.getDate("created_at").toLocalDate())
                    .setDeliveryDate(rs.getDate("delivery_at").toLocalDate())
                    .setOrderStatus(OrderStatus.values()[rs.getInt("status_id") - 1])
                    .setCustomer(addCustomerForTransportation(rs.getInt("user_id")))
                    .setTariff(addTariffForTransportation(rs.getInt("tariff_id")))
                    .build();

        return tr;
    }

    private User addCustomerForTransportation(int id){
            User user = new User();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id=?")){
            ps.setLong(1, id);

            ResultSet rst = ps.executeQuery();

            user = getUser(rst);
//            rst.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        if (resultSet.next()) {
            user = new UserBuilder().setId(resultSet.getInt("id"))
                    .setUsername(resultSet.getString("username"))
                    .setPassword(resultSet.getString("password"))
                    .setEmail(resultSet.getString("email"))
                    .setUserRole(Role.values()[resultSet.getInt("role_id") - 1])
                    .build();

        }
        return user;
    }

    private Tariff addTariffForTransportation(int id){
        Tariff tariff = new Tariff();
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM tariffs WHERE id=?")){
            ps.setLong(1, id);

            ResultSet rst = ps.executeQuery();

            tariff = mapTariff(rst);
//            rst.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return tariff;
    }

    private Tariff mapTariff(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        if(rs.next()) {
            tariff = new TariffBuilder().setId(rs.getInt("id"))
                    .setPrice(rs.getInt("price"))
                    .setAddress(Address.valueOf(rs.getString("address")))
                    .setSize(Size.valueOf(rs.getString("size")))
                    .setWeight(Weight.valueOf(rs.getString("weight")))
                    .setDeliveryTermDays(rs.getInt("delivery_term_days"))
                    .build();
        }

        return tariff;
    }

    /**
     * Saves new user's authorities: user name and user role.
     */
    @Override
    public boolean saveStatus(Integer id, OrderStatus status) {
        LOGGER.debug("Updating current transportation set status--> " + status.name());

        try(PreparedStatement pstm = connection.prepareStatement("UPDATE transportations set status_id=? WHERE id=?;")){

            pstm.setInt(1, status.ordinal() + 1);
            pstm.setInt(2, id);

            pstm.executeUpdate();

            LOGGER.debug("Transportation with id" + id + " has been saved");
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    /**
     * Fetches all user's transportations from database.
     * @return List of transportations or an empty list
     */
    @Override
    public List<Transportation> findTransportationByUserId(Integer id, Integer offset, Integer size, String sortDirection, String sortBy) {
        LOGGER.debug("Creating a list of user's transportations");

        List<Transportation> userTransportations = new ArrayList<>();
        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM transportations WHERE user_id=? ORDER BY " +
                sortBy + " " + sortDirection + " LIMIT ?, ?")) {
            connection.setAutoCommit(false);

            pstm.setInt(1, id);
            pstm.setInt(2, offset);
            pstm.setInt(3, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                userTransportations.add(getTransportation(rs));

            }
            rs.close();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
        }

        return userTransportations;
    }

    @Override
    public List<Transportation> findPages(Integer offset, Integer size, String sortDirection, String sortBy){
        LOGGER.debug("Getting page with offset " + offset + ", size " + size);

        List<Transportation> temp = new ArrayList<>();
        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM transportations ORDER BY " +
                                        sortBy + " " + sortDirection + " LIMIT ?, ?")){
            connection.setAutoCommit(false);
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                temp.add(getTransportation(rs));
            }
            rs.close();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
        }
        return temp;
    }

    @Override
    public List<Transportation> findTransportationByAddress(Integer offset, Integer size, String sortDirection, String address){
        LOGGER.debug("Getting transportation by address with offset " + offset + ", size " + size);
        List<Transportation> transportations = new ArrayList<>();

        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM transportations tr JOIN users u ON tr.user_id=u.id JOIN tariffs t ON tr.tariff_id=t.id WHERE t.address LIKE ? ORDER BY tr.id " +
                 sortDirection + " LIMIT ?, ?")){ //

            pstm.setString(1, address);
            pstm.setInt(2, offset);
            pstm.setInt(3, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                Transportation temp = createTransportation(rs);
                transportations.add(temp);
            }
            rs.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());

        }
        return transportations;

    }

    @Override
    public List<Transportation> findTransportationByDate(Integer offset, Integer size, String sortDirection, String sortBy, LocalDate date){
        LOGGER.debug("Getting transportation by date with offset " + offset + ", size " + size);
        List<Transportation> transportations = new ArrayList<>();

        try(PreparedStatement pstm = connection.prepareStatement("SELECT * FROM transportations tr JOIN users u ON tr.user_id=u.id JOIN tariffs t ON tr.tariff_id=t.id WHERE tr.created_at LIKE ? ORDER BY " +
                "tr.id " + sortDirection + " LIMIT ?, ?")){

            pstm.setString(1, date.toString());
            pstm.setInt(2, offset);
            pstm.setInt(3, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                Transportation temp = createTransportation(rs);
                transportations.add(temp);

            }
            rs.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());

        }
        return transportations;
    }

    private Transportation createTransportation(ResultSet rs) throws SQLException {
        Transportation t = new Transportation();

        t.setId(rs.getInt("id"));
        t.setComment(rs.getString("comment"));
        t.setCreationDate(rs.getDate("created_at").toLocalDate());
        t.setDeliveryDate(rs.getDate("delivery_at").toLocalDate());
        t.setOrderStatus(OrderStatus.values()[rs.getInt("status_id") - 1]);

        Tariff tf = new Tariff();
        tf.setAddress(Address.valueOf(rs.getString("address")));
        tf.setPrice(rs.getInt("price"));
        t.setTariff(tf);

        User u = new User();
        u.setUsername(rs.getString("username"));
        t.setCustomer(u);

        return t;
    }

    @Override
    public void close() {
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);//TODO logger + work out
        }
    }

    private void rollbackQuiet(Connection con){
        try {
            con.rollback();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
