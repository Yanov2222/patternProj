package ua.nure.patternProj.dao;

import ua.nure.patternProj.observer.EventManager;

public interface GenericDAO<T> {
    EventManager events = new EventManager("create","read","update","delete");
    boolean create(T obj);
    T read(T obj);
    void update(T obj);
    void delete(T obj);
}
