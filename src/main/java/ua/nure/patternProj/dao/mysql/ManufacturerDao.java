package ua.nure.patternProj.dao.mysql;

import ua.nure.patternProj.dao.DbManager;
import ua.nure.patternProj.dao.IManufacturerDao;
import ua.nure.patternProj.dao.mysql.entity.Manufacturer;

import java.sql.*;

public class ManufacturerDao implements IManufacturerDao {

    private static final String READ_MANUFACTURER = "select * from manufacturer where name=?";
    private static final String INSERT_MANUFACTURER = "insert into manufacturer(name) values(?)";


    @Override
    public boolean create(Manufacturer obj) {
        return false;
    }

    @Override
    public Manufacturer read(Manufacturer obj) {
        return null;
    }

    @Override
    public void update(Manufacturer obj) {

    }

    @Override
    public void delete(Manufacturer obj) {

    }

    @Override
    public Manufacturer manufacturerTransaction(Manufacturer obj) {
        Manufacturer manufacturer = null;
        try {
            Connection con = DbManager.getInstance().getConnection();
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            PreparedStatement statement = con.prepareStatement(READ_MANUFACTURER);
            statement.setString(1,obj.getName());
            ResultSet resultSet = statement.executeQuery();
            int manufacturerId = 0;
            if(resultSet.next()){
                manufacturerId = resultSet.getInt("id");
            }
            else {
                PreparedStatement insertManufacturer = con.prepareStatement(INSERT_MANUFACTURER, Statement.RETURN_GENERATED_KEYS);
                insertManufacturer.setString(1,obj.getName());
                insertManufacturer.executeUpdate();
                ResultSet generatedKeys = insertManufacturer.getGeneratedKeys();
                if(generatedKeys.next()){
                    manufacturerId = generatedKeys.getInt(1);
                    con.commit();
                }
                else {
                    con.rollback();
                }
            }
            manufacturer = Manufacturer.builder().addId(manufacturerId).addManufacturerName(obj.getName()).build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturer;
    }
}
