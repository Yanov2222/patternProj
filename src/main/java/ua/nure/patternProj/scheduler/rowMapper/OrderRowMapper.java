package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {

        return Order.builder()
                .addId(resultSet.getInt("id"))
                .addAddress(resultSet.getString("address"))
                .addSum(resultSet.getDouble("sum"))
                .addOrderingDate(resultSet.getDate("ordering_date"))
                .addUserId(resultSet.getInt("users_id"))
                .addUuid(resultSet.getString("uuid"))
                .build();
    }
}
