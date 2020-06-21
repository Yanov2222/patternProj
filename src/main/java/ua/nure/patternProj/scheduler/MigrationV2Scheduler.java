package ua.nure.patternProj.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.AutoRepository;
import ua.nure.patternProj.dao.mongodb.OrderRepository;
import ua.nure.patternProj.dao.mongodb.UserRepository;
import ua.nure.patternProj.dao.mysql.entity.Auto;
import ua.nure.patternProj.dao.mysql.entity.Order;
import ua.nure.patternProj.dao.mysql.entity.ShopCart;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.loader.AutoLoader;
import ua.nure.patternProj.scheduler.loader.OrderLoader;
import ua.nure.patternProj.scheduler.loader.ShopCartLoader;
import ua.nure.patternProj.scheduler.loader.UserLoader;
import ua.nure.patternProj.scheduler.rowMapper.UserRowMapper;
import ua.nure.patternProj.scheduler.transformer.UserTransformer;

import java.util.List;
import java.util.stream.Collectors;

// MongoDB to MySQL
@Slf4j
@Component
@ConditionalOnProperty(
        value="migration.mysql.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class MigrationV2Scheduler {

    @Autowired
    private UserLoader userLoader;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AutoLoader autoLoader;

    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderLoader orderLoader;
    @Autowired
    private ShopCartLoader shopCartLoader;

    //each hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void startMigration() {

        /** USER */
        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers = userRepository.findAll();
        List<User> trUsers = loadUsers.stream().map(user -> userLoader.loadFrom(user)).peek(this::saveUser).collect(Collectors.toList());
        /** AUTO */
        List<ua.nure.patternProj.dao.mongodb.entity.Auto> loadAutos = autoRepository.findAll();
        List<Auto> trAutos = loadAutos.stream().map(auto -> autoLoader.loadFrom(auto)).peek(this::saveAuto).collect(Collectors.toList());
        /** ORDER */
        List<ua.nure.patternProj.dao.mongodb.entity.Order> loadOrders = orderRepository.findAll();
        List<Order> trOrders = loadOrders.stream().map(order -> orderLoader.loadFrom(order)).peek(this::saveOrder).collect(Collectors.toList());
        List<ShopCart> trShopCart = loadOrders.stream().map(order -> shopCartLoader.loadFrom(order)).peek(this::saveShopCart).collect(Collectors.toList());

    }

    private int saveUser(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (login, password, email, user_name, telephone, status_id, uuid) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);",
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getTelephone(),
                user.getStatusId(),
                user.getUuid());
    }

    private int saveAuto(Auto auto){
        return jdbcTemplate.update(
                "INSERT INTO auto (seats,model,price,has_baby_seat,has_conditioner,has_bar,manufacturer_id,uuid)" +
                        "VALUES(?,?,?,?,?,?,?,?)",
                auto.getSeats(),
                auto.getModel(),
                auto.getPrice(),
                auto.getHasBabySeat(),
                auto.getHasConditioner(),
                auto.getHasBar(),
                auto.getManufacturerId(),
                auto.getUuid());
    }

    private int saveOrder(Order order){
        return jdbcTemplate.update(
                "INSERT INTO orders(ordering_date,sum,address,users_id,uuid) VALUES(?,?,?,?,?)",
                order.getOrderingDate(),
                order.getSum(),
                order.getAddress(),
                order.getUserId(),
                order.getUuid());
    }

    private int saveShopCart(ShopCart shopCart){
        for(Integer e: shopCart.getIdAuto()){
            jdbcTemplate.update("INSERT INTO shopcart(orders_id,auto_id) VALUES(?,?)",
                    shopCart.getIdOrder(), e);
        }
        return 1;
    }

}
