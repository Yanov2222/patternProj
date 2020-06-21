package ua.nure.patternProj.scheduler.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.entity.Auto;
import ua.nure.patternProj.dao.mongodb.entity.Order;
import ua.nure.patternProj.dao.mongodb.entity.User;

import java.util.List;

@Component
public class OrderTransformer {

    @Autowired
    private JdbcTemplate template;

    public Order mapTo(ua.nure.patternProj.dao.mysql.entity.Order entity, List<User> users, List<Auto> autos){
        List<String> autosUUids  = template.queryForList("SELECT a.uuid FROM orders as o " +
              "join shopcart as s on s.orders_id = o.id " +
            "join auto a on s.auto_id = a.id where o.id = ? group by a.uuid", new Object[]{entity.getId()},String.class);

        String  uuidU = template.queryForObject("SELECT u.uuid FROM orders o " +
                "inner join users u on o.users_id=u.id WHERE o.id = ?", new Object[]{entity.getId()},String.class);

        Order order = Order.builder().build();

        autos.stream().filter(auto -> autosUUids.contains(auto.getUuid())).forEach(a -> order.getAutos().add(a));
        users.stream().filter(user -> uuidU.equalsIgnoreCase(user.getUuid())).findFirst().ifPresent(order::setUser);
        order.setSum(entity.getSum());
        order.setAddress(entity.getAddress());
        order.setOrderingDate(entity.getOrderingDate());
        order.setUuid(entity.getUuid());

        return order;
    }
}
