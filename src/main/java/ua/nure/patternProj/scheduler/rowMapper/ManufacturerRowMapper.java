package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Manufacturer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ManufacturerRowMapper implements RowMapper<Manufacturer> {
    @Override
    public Manufacturer mapRow(ResultSet resultSet, int i) throws SQLException {
        return Manufacturer.builder()
                .addId(resultSet.getInt("id"))
                .addManufacturerName(resultSet.getString("name"))
                .build();
    }
}
