package ua.nure.patternProj.dao.mysql;

import ua.nure.patternProj.dao.DbManager;
import ua.nure.patternProj.dao.IAutoDao;
import ua.nure.patternProj.dao.mysql.entity.Auto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutoDao implements IAutoDao<Auto> {
    Connection con;

    public AutoDao(){
        try {
             con = DbManager.getInstance().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String ADD_AUTO = "INSERT INTO auto(seats,model,price,has_baby_seat,has_conditioner,has_bar,manufacturer_id, uuid)" +
            "VALUES (?,?,?,?,?,?,?,?)";
    private static final String READ_ALL_AUTO = "SELECT * FROM auto";
    private static final String GET_BY_UUID = "SELECT * FROM auto WHERE uuid=?";
    private static final String UPDATE_AUTO = "UPDATE AUTO SET seats=?, model=?, price=?," +
            "has_baby_seat=?,has_conditioner=?, has_bar=?, manufacturer_id=? WHERE uuid=?";
    private static final String DELETE_AUTO = "DELETE FROM AUTO WHERE uuid=?";
    private static final String SEARCH = "SELECT * FROM auto WHERE price > ? AND price < ? AND " +
            "has_baby_seat=? AND has_conditioner =? AND has_bar = ?";

    @Override
    public synchronized boolean create(Auto obj) {
        obj.setUuid(UUID.randomUUID().toString());
        try {

//            long startTime = System.currentTimeMillis();

            PreparedStatement statement = con.prepareStatement(ADD_AUTO, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, obj.getSeats());
            statement.setString(2, obj.getModel());
            statement.setInt(3, obj.getPrice());
            statement.setInt(4, obj.getHasBabySeat());
            statement.setInt(5, obj.getHasConditioner());
            statement.setInt(6, obj.getHasBar());
            statement.setInt(7, obj.getManufacturerId());
            statement.setString(8, obj.getUuid());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

//            long endTime = System.currentTimeMillis();
//            System.out.println("Total execution time: " + (endTime - startTime) + "ms");
            if (resultSet.next()) {
                events.notify("create", this);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Auto read(Auto obj) {
        return null;
    }

    @Override
    public void update(Auto obj) {
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(UPDATE_AUTO);
            statement.setInt(1, obj.getSeats());
            statement.setString(2, obj.getModel());
            statement.setInt(3, obj.getPrice());
            statement.setInt(4, obj.getHasBabySeat());
            statement.setInt(5, obj.getHasConditioner());
            statement.setInt(6, obj.getHasBar());
            statement.setInt(7, obj.getManufacturerId());
            statement.setString(8,obj.getUuid());
            statement.executeUpdate();
            events.notify("update",this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Auto obj) {
        try {
            Connection connection = DbManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_AUTO);
            statement.setString(1, obj.getUuid());
            statement.executeUpdate();
            events.notify("delete", this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Auto> getAllAuto() {
        List<Auto> list = new ArrayList<>();
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(READ_ALL_AUTO);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Auto auto = Auto.builder()
                        .addModel(resultSet.getString("model"))
                        .addId(resultSet.getInt("id"))
                        .addSeats(resultSet.getInt("seats"))
                        .addPrice(resultSet.getInt("price"))
                        .addConditioner(resultSet.getInt("has_conditioner"))
                        .addBabySeat(resultSet.getInt("has_baby_seat"))
                        .addBar(resultSet.getInt("has_bar"))
                        .addManufacturerId(resultSet.getInt("manufacturer_id"))
                        .build();
                list.add(auto);
            }
            events.notify("read", this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Auto getByUuid(String uuid) {
        Auto auto = null;
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(GET_BY_UUID);
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                auto = Auto.builder()
                        .addModel(resultSet.getString("model"))
                        .addId(resultSet.getInt("id"))
                        .addSeats(resultSet.getInt("seats"))
                        .addPrice(resultSet.getInt("price"))
                        .addConditioner(resultSet.getInt("has_conditioner"))
                        .addBabySeat(resultSet.getInt("has_baby_seat"))
                        .addBar(resultSet.getInt("has_bar"))
                        .addManufacturerId(resultSet.getInt("manufacturer_id"))
                        .build();
            }
            events.notify("read", this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auto;
    }

    @Override
    public List<Auto> getAutoByParameter(int minPrice, int maxPrice, int hasBabySeat, int hasConditioner, int hasBar) {
        List<Auto> list = new ArrayList<>();
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SEARCH);
            statement.setInt(1, minPrice);
            statement.setInt(2, maxPrice);
            statement.setInt(3, hasBabySeat);
            statement.setInt(4, hasConditioner);
            statement.setInt(5, hasBar);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Auto auto = Auto.builder()
                        .addModel(resultSet.getString("model"))
                        .addId(resultSet.getInt("id"))
                        .addSeats(resultSet.getInt("seats"))
                        .addPrice(resultSet.getInt("price"))
                        .addConditioner(resultSet.getInt("has_conditioner"))
                        .addBabySeat(resultSet.getInt("has_baby_seat"))
                        .addBar(resultSet.getInt("has_bar"))
                        .addManufacturerId(resultSet.getInt("manufacturer_id"))
                        .build();
                list.add(auto);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
