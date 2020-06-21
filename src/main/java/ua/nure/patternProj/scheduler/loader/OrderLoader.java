package ua.nure.patternProj.scheduler.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Order;
import ua.nure.patternProj.dao.mysql.entity.ShopCart;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.rowMapper.OrderRowMapper;
import ua.nure.patternProj.scheduler.rowMapper.UserRowMapper;

@Component
public class OrderLoader {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderRowMapper orderRowMapper;
    @Autowired
    private UserRowMapper userRowMapper;

    public Order loadFrom(ua.nure.patternProj.dao.mongodb.entity.Order entity){
        int userId = 0;
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE uuid=?",new Object[]{entity.getUser().getUuid()},userRowMapper);
        userId = user.getId();
        return Order.builder()
                .addAddress(entity.getAddress())
                .addSum(entity.getSum())
                .addOrderingDate(entity.getOrderingDate())
                .addUserId(userId)
                .addUuid(entity.getUuid())
                .build();
    }
}
