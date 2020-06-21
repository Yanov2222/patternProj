package ua.nure.patternProj.scheduler.transformer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.entity.Auto;
import ua.nure.patternProj.dao.mysql.entity.Manufacturer;
import ua.nure.patternProj.scheduler.rowMapper.ManufacturerRowMapper;

import javax.management.Query;

@Component
public class AutoTransformer {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private ManufacturerRowMapper manufacturerRowMapper;

    public Auto mapTo(ua.nure.patternProj.dao.mysql.entity.Auto entity){
        Manufacturer manufacturer = template
                .queryForObject("SELECT * FROM manufacturer WHERE id=?"
                        ,new Object[]{entity.getManufacturerId()}
                        ,manufacturerRowMapper);
        return Auto.builder()
                .model(entity.getModel())
                .price(entity.getPrice())
                .seats(entity.getSeats())
                .hasBabySeat(entity.getHasBabySeat())
                .hasConditioner(entity.getHasConditioner())
                .hasBar(entity.getHasBar())
                .uuid(entity.getUuid())
                .manufacturer(manufacturer.getName())
                .build();
    }
}
