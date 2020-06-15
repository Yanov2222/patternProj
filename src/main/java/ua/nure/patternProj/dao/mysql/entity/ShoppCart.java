package ua.nure.patternProj.dao.mysql.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ShoppCart {
    private int idOrder;
    private List<Integer> idAuto = new ArrayList<>();

    public static ShoppCartBuilder builder(){
        return new ShoppCartBuilder();
    }

    public static class ShoppCartBuilder{
        private ShoppCart shoppCart;

        public ShoppCartBuilder addIdOrder(int idOrder){
            shoppCart.idOrder = idOrder;
            return this;
        }
        public ShoppCartBuilder addIdAuto(int idAuto){
            shoppCart.idAuto.add(idAuto);
            return this;
        }
        public ShoppCart build(){
            return shoppCart;
        }
    }
}
