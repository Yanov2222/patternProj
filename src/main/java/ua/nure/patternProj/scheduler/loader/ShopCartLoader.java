package ua.nure.patternProj.scheduler.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.entity.Auto;
import ua.nure.patternProj.dao.mongodb.entity.Order;
import ua.nure.patternProj.dao.mysql.entity.ShopCart;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShopCartLoader {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ShopCart loadFrom(Order entity){
        ShopCart shopCart = new ShopCart();
        int orderId = jdbcTemplate.queryForObject("SELECT id FROM orders WHERE uuid=?", new Object[]{entity.getUuid()},Integer.class);
        shopCart.setIdOrder(orderId);
        List<Integer> list = new ArrayList<>();
        for(Auto e: entity.getAutos()){
            list.add(jdbcTemplate.queryForObject("SELECT id FROM auto WHERE uuid=?",new Object[]{e.getUuid()},Integer.class));
        }
        shopCart.setIdAuto(list);
        return shopCart;
    }

}
