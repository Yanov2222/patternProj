package ua.nure.patternProj.dao.mysql;

import ua.nure.patternProj.dao.*;
import ua.nure.patternProj.dao.mysql.entity.*;

public class MysqlDaoFactory extends DAOFactory {
    private static MysqlDaoFactory instance;
    private MysqlDaoFactory(){}

    public static MysqlDaoFactory getInstance(){
        synchronized (MysqlDaoFactory.class){
            if(instance == null){
                instance = new MysqlDaoFactory();
            }
        }
        return instance;
    }


    @Override
    public IUserDao getUserDao() {
        return new UserDao();
    }

    @Override
    public GenericDAO<Order> getOrderDao() {
        return null;
    }

    @Override
    public GenericDAO<Role> getRoleDao() {
        return null;
    }

    @Override
    public GenericDAO<ShoppCart> getShoppcartDao() {
        return null;
    }

    @Override
    public IAutoDao getAutoDao() {
        return new AutoDao();
    }

    @Override
    public IManufacturerDao getManufacturerDao() {
        return new ManufacturerDao();
    }
}
