package ua.nure.patternProj.dao;

import ua.nure.patternProj.dao.mysql.entity.Manufacturer;

public interface IManufacturerDao extends GenericDAO<Manufacturer> {

    public Manufacturer manufacturerTransaction(Manufacturer obj);
}
