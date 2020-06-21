package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Auto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AutoRowMapper implements RowMapper<Auto> {
    @Override
    public Auto mapRow(ResultSet resultSet, int i) throws SQLException {
        return Auto.builder()
                .addId(resultSet.getInt("id"))
                .addUuid(resultSet.getString("uuid"))
                .addModel(resultSet.getString("model"))
                .addPrice(resultSet.getInt("price"))
                .addSeats(resultSet.getInt("seats"))
                .addBabySeat(resultSet.getInt("has_baby_seat"))
                .addConditioner(resultSet.getInt("has_conditioner"))
                .addManufacturerId(resultSet.getInt("manufacturer_id"))
                .addBar(resultSet.getInt("has_bar"))
                .build();
    }
}
