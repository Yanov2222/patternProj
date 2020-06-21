package ua.nure.patternProj.dao.mysql.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShopCart {
    private int idOrder;
    private List<Integer> idAuto = new ArrayList<>();

    public void setIdOrder(int idOrder){
        this.idOrder = idOrder;
    }

    public void setIdAuto(List<Integer> list){
        this.idAuto = list;
    }
}
