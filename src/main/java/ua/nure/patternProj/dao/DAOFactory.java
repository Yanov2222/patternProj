package ua.nure.patternProj.dao;

import ua.nure.patternProj.dao.mysql.entity.*;


public abstract class DAOFactory {
    public abstract IUserDao getUserDao();
    public abstract GenericDAO<Order> getOrderDao();
    public abstract GenericDAO<Role> getRoleDao();
    public abstract GenericDAO<ShoppCart> getShoppcartDao();
    public abstract IAutoDao getAutoDao();
    public abstract IManufacturerDao getManufacturerDao();
}
