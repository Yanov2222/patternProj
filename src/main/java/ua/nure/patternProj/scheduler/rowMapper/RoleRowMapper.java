package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Role;
import ua.nure.patternProj.dao.mysql.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {

        return Role.builder().addId(rs.getInt("id"))
                .addRoleName(rs.getString("role_name"))
                .build();
    }
}
