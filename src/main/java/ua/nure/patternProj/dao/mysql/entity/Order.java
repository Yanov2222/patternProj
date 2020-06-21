package ua.nure.patternProj.dao.mysql.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Order {
    private int id;
    private Date orderingDate;
    private double sum;
    private String address;
    private int userId;
    private String uuid;


    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Order order;

        public OrderBuilder(){ this.order = new Order();}

        public OrderBuilder addId(int id){
            order.id = id;
            return this;
        }
        public OrderBuilder addOrderingDate(Date date){
            order.orderingDate = date;
            return this;
        }
        public OrderBuilder addSum(double sum){
            order.sum=sum;
            return this;
        }
        public OrderBuilder addAddress(String address){
            order.address = new String(order.address +"; " + address);
            return this;
        }
        public OrderBuilder addUserId(int userId){
            order.userId = userId;
            return this;
        }
        public OrderBuilder addUuid(String uuid){
            order.uuid = uuid;
            return this;
        }
        public Order build(){
            return order;
        }
    }
}
