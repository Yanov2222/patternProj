package ua.nure.patternProj.dao.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.nure.patternProj.dao.mongodb.entity.Order;
import ua.nure.patternProj.dao.mongodb.entity.User;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}