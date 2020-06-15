package ua.nure.patternProj.observer;

import ua.nure.patternProj.dao.GenericDAO;

public interface EventListener {
    void update(String eventType, GenericDAO dao);
}
