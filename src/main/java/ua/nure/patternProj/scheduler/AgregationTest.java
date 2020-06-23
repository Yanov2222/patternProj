package ua.nure.patternProj.scheduler;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.nure.patternProj.dao.mongodb.entity.Auto;

import java.util.ArrayList;
import java.util.List;

@Component
public class AgregationTest {

    @Autowired
    private MongoTemplate template;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void testTimeAgregation(){
        //QUERY 1 NO AGREGATION
        Query query = new Query().addCriteria(Criteria.where("model").is("BMW M2"));
        long startTime = System.currentTimeMillis();
        List<Auto> query1NoAgregation = template.find(query,Auto.class);
        long endTime = System.currentTimeMillis();
        System.out.println("Query1 no agregation: " + (endTime-startTime) + "ms");

        //QUERY 1 AGREGATION
        MatchOperation matchOperation = Aggregation.match(new Criteria("model").is("BMW M2"));
        ProjectionOperation projectionOperation = Aggregation.project("model","price","seats");
        Aggregation query1 = Aggregation.newAggregation(matchOperation, projectionOperation);

        startTime = System.currentTimeMillis();
        AggregationResults<Auto> query1Agregation = template.aggregate(query1,"auto",Auto.class);
        endTime = System.currentTimeMillis();

        System.out.println("Query1 agregation: " + (endTime-startTime) + "ms");
        System.out.println("--------------");


        //QUERY 2 NO AGREGATION
        query = new Query().addCriteria(Criteria.where("model").is("BMW M2"));
        startTime = System.currentTimeMillis();
        List<Auto> query2NoAgregation = template.find(query,Auto.class);
        int avgPrice = 0;
        for(Auto a: query2NoAgregation){
            avgPrice += a.getPrice();
        }
        avgPrice/= query2NoAgregation.size();
        System.out.println("Avg price = " + avgPrice);
        endTime = System.currentTimeMillis();
        System.out.println("Query2 no agregation: " + (endTime-startTime) + "ms");

        //QUERY 2 AGREGATION
        MatchOperation matchOperation2 = Aggregation.match(new Criteria("model").is("BMW M2"));
        GroupOperation groupOperation2 = Aggregation.group("model").avg("price").as("avgprice");
        Aggregation query3 = Aggregation.newAggregation(matchOperation2,groupOperation2);

        startTime = System.currentTimeMillis();
        AggregationResults<Auto> query2Agregation = template.aggregate(query3,"auto",Auto.class);
        System.out.println(query2Agregation.getRawResults().get("results"));
        endTime = System.currentTimeMillis();
        System.out.println("Query2 agregation: " + (endTime-startTime) + "ms");
        System.out.println("--------------");


        //QUERY 3 NO AGREGATION
        query = new Query().addCriteria(Criteria.where("manufacturer").is("Tesla"));
        startTime = System.currentTimeMillis();
        List<Auto> query3NoAgregation = template.find(query,Auto.class);
        int count = 0;
        for (Auto a: query3NoAgregation){
            count++;
        }
        System.out.println("Autos count: " + count);
        endTime = System.currentTimeMillis();
        System.out.println("Query3 no agregation: " + (endTime-startTime) + "ms");


        //QUERY 3 AGREGATION
        matchOperation = Aggregation.match(new Criteria("manufacturer").is("Tesla"));
        groupOperation2 = Aggregation.group("manufacturer").count().as("count");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation2);

        startTime = System.currentTimeMillis();
        AggregationResults<Auto> query3Agregation = template.aggregate(aggregation,"auto",Auto.class);
        System.out.println(query3Agregation.getRawResults().get("results"));
        endTime = System.currentTimeMillis();
        System.out.println("Query3 agregation: " + (endTime-startTime) + "ms");
        System.out.println("--------------");
        //QUERY 4 NO AGREGATION

        query = new Query(Criteria.where("model").regex("Audi"));

        startTime = System.currentTimeMillis();
        List<Auto> query4NoAgregation = template.find(query,Auto.class);
        avgPrice = 0;
        for (Auto a: query4NoAgregation){
            avgPrice += a.getPrice();
        }
        avgPrice/=query4NoAgregation.size();
        System.out.println("Avg price Audi: " + avgPrice);
        endTime = System.currentTimeMillis();
        System.out.println("Query4 no agregation: " + (endTime-startTime) + "ms");
        //QUERY 4 AGGREGATION
        matchOperation = Aggregation.match(new Criteria("model").regex("Audi"));
        groupOperation2 = Aggregation.group("model").avg("price").as("avgPrice");
        aggregation = Aggregation.newAggregation(matchOperation, groupOperation2);

        startTime = System.currentTimeMillis();
        AggregationResults<Auto> query4Aggregation = template.aggregate(aggregation,"auto", Auto.class);
        System.out.println(query4Aggregation.getRawResults().get("results"));
        endTime = System.currentTimeMillis();
        System.out.println("Query4 agregation: " + (endTime-startTime) + "ms");
        System.out.println("--------------");


        //QUERY 5 NO AGGREGATION
        query = new Query(Criteria.where("manufacturer").is("Audi").and("model").regex("A7"));
        startTime = System.currentTimeMillis();
        List<Auto> query5NoAgregation = template.find(query, Auto.class);
        int maxPrice = 0;
        for (Auto a: query5NoAgregation){
            if(a.getPrice()>maxPrice){
                maxPrice = a.getPrice();
            }
        }
        System.out.println("Max price: " + maxPrice);
        endTime = System.currentTimeMillis();
        System.out.println("Query5 no agregation: " + (endTime-startTime) + "ms");

        //QUERY 5 AGGREGATION
        matchOperation = Aggregation.match(new Criteria("manufacturer").is("Audi").and("model").regex("A7"));
        groupOperation2 = Aggregation.group("model").max("price").as("maxPrice");
        aggregation = Aggregation.newAggregation(matchOperation,groupOperation2);

        startTime = System.currentTimeMillis();
        AggregationResults<Auto> query5Aggregation = template.aggregate(aggregation,"auto",Auto.class);
        System.out.println(query5Aggregation.getRawResults().get("results"));
        endTime = System.currentTimeMillis();
        System.out.println("Query5 agregation: " + (endTime-startTime) + "ms");
    }
}
