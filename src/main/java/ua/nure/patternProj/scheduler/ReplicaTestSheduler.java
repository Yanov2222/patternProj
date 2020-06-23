package ua.nure.patternProj.scheduler;


import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.DAOFactory;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mongodb.MongoDaoFactory;
import ua.nure.patternProj.dao.mongodb.MongoUserDao;
import ua.nure.patternProj.dao.mongodb.entity.User;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
public class ReplicaTestSheduler {

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void init(){
        DAOFactory factory = MongoDaoFactory.getInstance();
        IUserDao<User> mongoUserDao = factory.getUserDao();
//        MongoClient client = new MongoClient("localhost", 27017);
        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost",40000),
                new ServerAddress("localhost",40001),
                new ServerAddress("localhost", 40002)));
        MongoDatabase db = mongoClient.getDatabase("taxi");
        MongoCollection<Document> coll = db.getCollection("users");
        coll.deleteMany(new Document());

        for (int i = 0; i < 1000; i++) {
            User user = User.builder()
                    .login("testLogin")
                    .email("testEmail")
                    .name("testName")
                    .password("testPassword")
                    .role("USER")
                    .telephone("09090090")
                    .uuid(UUID.randomUUID().toString())
                    .build();
            mongoUserDao.createIntoReplica(user,mongoClient, WriteConcern.JOURNALED);
        }

        System.out.println("Inserted docs count: " + coll.countDocuments());
    }
}
