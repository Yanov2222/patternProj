package ua.nure.patternProj.dao;

import ua.nure.patternProj.dao.mysql.entity.Auto;

import java.util.List;

public interface IAutoDao<T> extends GenericDAO<T> {
    public List<T> getAllAuto();
    public T getByUuid(String uuid);
    public List<T> getAutoByParameter(int minPrice, int maxPrice, int hasBabySeat, int hasConditioner, int hasBar);
}
