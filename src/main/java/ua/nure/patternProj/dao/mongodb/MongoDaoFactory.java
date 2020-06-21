package ua.nure.patternProj.dao.mongodb;

import ua.nure.patternProj.dao.*;
import ua.nure.patternProj.dao.mysql.entity.Order;
import ua.nure.patternProj.dao.mysql.entity.Role;
import ua.nure.patternProj.dao.mysql.entity.ShoppCart;

public class MongoDaoFactory extends DAOFactory {
    private static MongoDaoFactory instance;
    private MongoDaoFactory(){}

    public static MongoDaoFactory getInstance(){
        synchronized (MongoDaoFactory.class){
            if(instance == null){
                instance = new MongoDaoFactory();
            }
        }
        return instance;
    }



    public IUserDao getUserDao() {
        return new MongoUserDao(DbManager.getInstance().getMConnection());
    }

    @Override
    public GenericDAO<Order> getOrderDao() {
        return null;
    }

    @Override
    public GenericDAO<Role> getRoleDao() {
        throw new UnsupportedOperationException();
    }

    @Override
    public GenericDAO<ShoppCart> getShoppcartDao() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IAutoDao getAutoDao() {
        return new MongoAutoDao(DbManager.getInstance().getMConnection());
    }

    @Override
    public IManufacturerDao getManufacturerDao() {
        throw new UnsupportedOperationException();
    }
}
