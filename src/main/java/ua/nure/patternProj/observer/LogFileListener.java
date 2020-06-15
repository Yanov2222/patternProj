package ua.nure.patternProj.observer;


import ua.nure.patternProj.dao.GenericDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class LogFileListener implements EventListener{

    private File file;

    public LogFileListener(String fileName){
        this.file = new File(fileName);
    }

    @Override
    public void update(String eventType, GenericDAO dao) {
        try(FileWriter writer = new FileWriter(file.getName(),true)){
            String text = new Date().toString() + "|| Event occured :" + eventType + "|| at " + dao.getClass().getName();
            writer.write(text);
            writer.append("\n\r");
            writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
