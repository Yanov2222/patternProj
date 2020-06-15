package ua.nure.patternProj.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.UserRepository;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.rowMapper.UserRowMapper;
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

    //each hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void startMigration() {

        //user
        List<User> users = exportUsers();
        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers =
                users.stream().map(user -> userTransformer.mapTo(user)).collect(Collectors.toList());

        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers2 = userRepository.saveAll(loadUsers);

       // loadUsers2.forEach(a -> log.info(a.toString()));
        //auto
    }


    private List<User> exportUsers() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(
                sql,
                userRowMapper);
    }


}
