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
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.rowMapper.AutoRowMapper;
import ua.nure.patternProj.scheduler.rowMapper.OrderRowMapper;
import ua.nure.patternProj.scheduler.rowMapper.UserRowMapper;
import ua.nure.patternProj.scheduler.transformer.AutoTransformer;
import ua.nure.patternProj.scheduler.transformer.OrderTransformer;
import ua.nure.patternProj.scheduler.transformer.UserTransformer;

import java.util.List;
import java.util.stream.Collectors;

// MySql to MongoDb
@Slf4j
@Component
@ConditionalOnProperty(
        value="migration.mongo.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class MigrationV1Scheduler {

    @Autowired
    private UserTransformer userTransformer;
    @Autowired
    private UserRowMapper userRowMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AutoTransformer autoTransformer;
    @Autowired
    private AutoRowMapper autoRowMapper;
    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private OrderRowMapper orderRowMapper;
    @Autowired
    private OrderTransformer orderTransformer;
    @Autowired
    private OrderRepository orderRepository;

    //each hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void startMigration() {

        //user
        List<User> users = exportUsers();
        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers =
                users.stream().map(user -> userTransformer.mapTo(user)).collect(Collectors.toList());

        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers2 = userRepository.saveAll(loadUsers); //1

       // loadUsers2.forEach(a -> log.info(a.toString()));
        //auto
        List<Auto> autos = exportAutos();
        List<ua.nure.patternProj.dao.mongodb.entity.Auto> loadAutos =
                autos.stream().map(auto -> autoTransformer.mapTo(auto)).collect(Collectors.toList());

        List<ua.nure.patternProj.dao.mongodb.entity.Auto> loadAutos2 = autoRepository.saveAll(loadAutos); //2

        /** ORDER */
        List<Order> orders = exportOrders();
        List<ua.nure.patternProj.dao.mongodb.entity.Order> loadOrders =
                orders.stream().map(order -> orderTransformer.mapTo(order,loadUsers2,loadAutos2)).collect(Collectors.toList());
        List<ua.nure.patternProj.dao.mongodb.entity.Order> loadOrders2 = orderRepository.saveAll(loadOrders);
    }


    private List<User> exportUsers() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(
                sql,
                userRowMapper);
    }

    private List<Auto> exportAutos() {
        String sql = "SELECT * FROM auto";

        return jdbcTemplate.query(
                sql,
                autoRowMapper);
    }

    private List<Order> exportOrders(){
        String sql = "SELECT * FROM orders";

        return jdbcTemplate.query(sql, orderRowMapper);
    }


}
