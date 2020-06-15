package ua.nure.patternProj.dao.mysql.entity;

import java.util.ArrayList;
import java.util.List;

public class AutoCaretaker {

    private Auto auto;
    private List<Auto.Memento> history;

    public AutoCaretaker(Auto auto){
        history = new ArrayList<>();
        this.auto = auto;
    }

    public void saveToHistory(Auto auto){
        history.add(auto.save());
    }

    public void undo(){

    }
}
