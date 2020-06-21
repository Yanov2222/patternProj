package ua.nure.patternProj.scheduler.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mysql.entity.Auto;
import ua.nure.patternProj.dao.mysql.entity.Manufacturer;
import ua.nure.patternProj.scheduler.rowMapper.AutoRowMapper;
import ua.nure.patternProj.scheduler.rowMapper.ManufacturerRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class AutoLoader {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AutoRowMapper autoRowMapper;

    @Autowired
    private ManufacturerRowMapper manufacturerRowMapper;

    public Auto loadFrom(ua.nure.patternProj.dao.mongodb.entity.Auto entity) {
        int manufacturerId = 0;
        Manufacturer manufacturer = null;
        try {
            manufacturer = jdbcTemplate.
                    queryForObject("SELECT * FROM manufacturer WHERE name=?",
                            new Object[]{entity.getManufacturer()}, manufacturerRowMapper);
        }
        catch (EmptyResultDataAccessException e){

        }
        if (manufacturer == null) {
            String INSERT_MANUFACTURER_SQL = "INSERT INTO manufacturer (name) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_MANUFACTURER_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getManufacturer());
                return ps;
            }, keyHolder);
            manufacturerId = keyHolder.getKey().intValue();
        } else {
            manufacturerId = manufacturer.getId();
        }
        return Auto.builder()
                .addModel(entity.getModel())
                .addUuid(entity.getUuid())
                .addSeats(entity.getSeats())
                .addPrice(entity.getPrice())
                .addBabySeat(entity.getHasBabySeat())
                .addConditioner(entity.getHasConditioner())
                .addBar(entity.getHasBar())
                .addManufacturerId(manufacturerId)
                .build();
    }
}
