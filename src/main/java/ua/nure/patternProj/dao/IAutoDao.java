package ua.nure.patternProj.dao;

import ua.nure.patternProj.dao.mysql.entity.Auto;

import java.util.List;

public interface IAutoDao extends GenericDAO<Auto> {
    public List<Auto> getAllAuto();
    public Auto getById(int id);
    public List<Auto> getAutoByParameter(int minPrice, int maxPrice, int hasBabySeat, int hasConditioner, int hasBar);
}
