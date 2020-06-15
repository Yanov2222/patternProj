package ua.nure.patternProj.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.UserRepository;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.loader.UserLoader;
import ua.nure.patternProj.scheduler.rowMapper.UserRowMapper;
import ua.nure.patternProj.scheduler.transformer.UserTransformer;

import java.util.List;
import java.util.stream.Collectors;

// MySql to MongoDb
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

    //each hour
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void startMigration() {


        List<ua.nure.patternProj.dao.mongodb.entity.User> loadUsers = userRepository.findAll();
        List<User> trUsers = loadUsers.stream().map(user -> userLoader.loadFrom(user)).peek(this::saveUser).collect(Collectors.toList());

        System.out.println("loh");
    }

    private int saveUser(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (id, login, password, email, user_name, telephone, status_id, uuid) " +
                        "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?);",
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getTelephone(),
                user.getStatusId(),
                user.getUuid());
    }



}
