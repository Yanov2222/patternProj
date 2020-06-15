package ua.nure.patternProj.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mongodb.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MongoUserDao implements IUserDao<User> {

    private final MongoTemplate template;

    public MongoUserDao(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public boolean create(User obj) {
        obj.setId(UUID.randomUUID().toString());
        obj.setRole("UNKNOWN");
        return Optional.ofNullable(template.insert(obj)).isPresent();
    }

    @Override
    public User read(User obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(obj.getLogin()));
        List<User> users = template.find(query, User.class);
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public void update(User obj) {
        //find
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(obj.getUuid()));
        Update update = new Update().set("email", obj.getEmail());
        template.upsert(query, update, User.class);
    }

    @Override
    public void delete(User obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(obj.getUuid()));
        template.remove(query);
    }

    public static void main(String[] args) {
        System.out.println(User.class.getName());
    }
}
