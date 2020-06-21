package ua.nure.patternProj.scheduler.transformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.entity.User;
import ua.nure.patternProj.dao.mysql.entity.Role;
import ua.nure.patternProj.scheduler.rowMapper.RoleRowMapper;

import java.util.List;

@Component
public class UserTransformer {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RoleRowMapper roleRowMapper;


    public User mapTo(ua.nure.patternProj.dao.mysql.entity.User entity){
        Role role = template.queryForObject("SELECT * FROM roles WHERE id = ?", new Object[]{entity.getStatusId()}, roleRowMapper);


        if(role == null){
            role = Role.builder().addRoleName("USER").build();
        }
        return User.builder().telephone(entity.getTelephone())
                .password(entity.getPassword())
                .name(entity.getName())
                .email(entity.getEmail())
                .login(entity.getLogin())
                .uuid(entity.getUuid())
                .role(role.getRoleName()).build();
    }
}
