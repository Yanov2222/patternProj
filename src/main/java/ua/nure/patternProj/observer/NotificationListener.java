package ua.nure.patternProj.observer;

import lombok.extern.slf4j.Slf4j;
import ua.nure.patternProj.dao.GenericDAO;

@Slf4j
public class NotificationListener implements EventListener {

    @Override
    public void update(String eventType, GenericDAO dao) {
            log.info("Event occured: " + eventType + " At: " + dao.getClass().getName());
    }


}
