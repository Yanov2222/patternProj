package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        return User.builder().addLogin(rs.getString("login"))
                .addPassword(rs.getString("password"))
                .addEmail(rs.getString("email"))
                .addName(rs.getString("user_name"))
                .addTelephone(rs.getString("telephone"))
                .addUuid(rs.getString("uuid"))
                .addStatusId(rs.getInt("status_id"))
                .build();
    }
}
