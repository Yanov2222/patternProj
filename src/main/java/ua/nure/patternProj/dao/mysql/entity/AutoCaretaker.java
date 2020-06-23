package ua.nure.patternProj.dao.mysql.entity;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Getter
public class AutoCaretaker {

    private List<Auto.Memento> history;

    public AutoCaretaker(){
        history = new ArrayList<>();
    }

    public void saveToHistory(Auto auto){
        history.add(auto.save());
    }

    public Auto.Memento getSnapshot(int id){
        Auto.Memento memento = null;
        for(Iterator<Auto.Memento> it = history.iterator(); it.hasNext();){
            Auto.Memento m = it.next();
            if(m.getId()==id){
                memento = m;
            }
        }
        return memento;
    }

    public void remove(int id){
        for(Iterator<Auto.Memento> it = history.iterator(); it.hasNext();){
            Auto.Memento m = it.next();
            if(m.getId()==id){
                it.remove();
            }
        }
    }
}
