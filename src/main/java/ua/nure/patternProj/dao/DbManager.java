package ua.nure.patternProj.dao;




import com.mongodb.MongoClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbManager {

    private static DbManager instance;

    public static DbManager getInstance(){
        synchronized (DbManager.class){
            if(instance==null){
                instance = new DbManager();
            }
        }
        return instance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mongo2","root","n1TjnC2Rt3");
        return con; //DbManager
    }

    public MongoTemplate getMConnection(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return new MongoTemplate(mongoClient, "taxi");
    }
}
