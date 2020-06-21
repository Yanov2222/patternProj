package ua.nure.patternProj.dao.mongodb;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ua.nure.patternProj.dao.IAutoDao;
import ua.nure.patternProj.dao.mongodb.entity.Auto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MongoAutoDao implements IAutoDao<Auto> {

    private final MongoTemplate template;

    public MongoAutoDao(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public List<Auto> getAllAuto() {
        List<Auto> autos = template.findAll(Auto.class);
        return autos;
    }

    @Override
    public Auto getByUuid(String uuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(uuid));
        List<Auto> autos = template.find(query, Auto.class);
        return autos.stream().findFirst().orElse(null);
    }

    @Override
    public List<Auto> getAutoByParameter(int minPrice, int maxPrice, int hasBabySeat, int hasConditioner, int hasBar) {
        Query query = new Query();
        query.addCriteria(Criteria.where("price").gt(minPrice).lte(maxPrice)
                .and("hasBabySeat").is(hasBabySeat)
                .and("hasConditioner").is(hasConditioner)
                .and("hasBar").is(hasBar));
        List<Auto> autos = template.find(query, Auto.class);
        return autos;
    }

    @Override
    public boolean create(Auto obj) {
        obj.setUuid(UUID.randomUUID().toString());
        return Optional.ofNullable(template.insert(obj)).isPresent();
    }

    @Override
    public Auto read(Auto obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("model").is(obj.getModel()));
        List<Auto> autos = template.find(query, Auto.class);
        return autos.stream().findFirst().orElse(null);
    }

    @Override
    public void update(Auto obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(obj.getUuid()));
        Update update = new Update().set("seats", obj.getSeats())
                .set("model", obj.getModel())
                .set("price", obj.getPrice())
                .set("hasBabySeat", obj.getHasBabySeat())
                .set("hasConditioner", obj.getHasConditioner())
                .set("hasBar", obj.getHasBar())
                .set("manufacturer", obj.getManufacturer());
        template.updateFirst(query,update,Auto.class);
    }

    @Override
    public void delete(Auto obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(obj.getUuid()));
        template.remove(query,Auto.class);
    }

}
