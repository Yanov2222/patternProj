package ua.nure.patternProj.dao.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.catalina.Server;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mongodb.entity.User;

import static com.mongodb.client.model.Filters.eq;

import java.util.*;

public class MongoUserDao implements IUserDao<User> {

    private final MongoTemplate template;

    public MongoUserDao(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public boolean create(User obj) {
        obj.setUuid(UUID.randomUUID().toString());
        obj.setRole("USER");

        return Optional.ofNullable(template.insert(obj)).isPresent();
    }

    @Override
    public boolean createIntoReplica(User obj, MongoClient mongoClient, WriteConcern concern) {
//        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost",27001),
//                new ServerAddress("localhost",27002),
//                new ServerAddress("localhost", 27003)));
        MongoDatabase db = mongoClient.getDatabase("taxi");
        MongoCollection<Document> coll = db.getCollection("users");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uuid", obj.getUuid());
        map.put("login", obj.getLogin());
        map.put("password", obj.getPassword());
        map.put("email", obj.getEmail());
        map.put("name", obj.getName());
        map.put("telephone", obj.getTelephone());
        map.put("role", obj.getRole());
        map.put("_class", User.class.getName());
        Document doc = new Document(map);
        for (int i = 0; i < 3; i++) {
            try {
                if (coll.find(eq("uuid", obj.getUuid())).first() != null) {
                    throw new MongoException("Such clothes already exsists");
                }
                coll.withWriteConcern(concern).insertOne(doc);
                return true;
            } catch (MongoException e){
                System.out.println(e);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public List<User> readAll(MongoClient mongoClient) {
        return null;

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

}
