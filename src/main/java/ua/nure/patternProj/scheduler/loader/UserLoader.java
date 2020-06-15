package ua.nure.patternProj.scheduler.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Role;
import ua.nure.patternProj.dao.mysql.entity.User;
import ua.nure.patternProj.scheduler.rowMapper.RoleRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class UserLoader {
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RoleRowMapper roleRowMapper;


    public User loadFrom(ua.nure.patternProj.dao.mongodb.entity.User entity) {
        int roleId = 0;
        Role role = template.queryForObject("SELECT * FROM roles WHERE name = ?", new Object[]{entity.getRole()}, roleRowMapper);
        if (role == null) {
            String INSERT_ROLE_SQL
                    = "insert into roles (role_name) values(?) ";


            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(INSERT_ROLE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getRole());
                return ps;
            }, keyHolder);
            roleId = keyHolder.getKey().intValue();
        } else {
            roleId = role.getId();
        }
        return User.builder().addLogin(entity.getLogin())
                .addPassword(entity.getPassword())
                .addEmail(entity.getEmail())
                .addName(entity.getName())
                .addTelephone(entity.getTelephone())
                .addUuid(entity.getUuid())
                .addStatusId(roleId)
                .build();
    }
}
