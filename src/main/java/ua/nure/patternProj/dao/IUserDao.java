package ua.nure.patternProj.dao;


import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import ua.nure.patternProj.dao.mongodb.entity.User;

import java.util.List;

public interface IUserDao<T> extends GenericDAO<T>{
    public boolean createIntoReplica(T obj, MongoClient mongoClient, WriteConcern concern);
    public List<T> readAll(MongoClient mongoClient);
}
