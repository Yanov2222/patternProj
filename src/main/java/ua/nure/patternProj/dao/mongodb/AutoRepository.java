package ua.nure.patternProj.dao.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.nure.patternProj.dao.mongodb.entity.Auto;

@Repository
public interface AutoRepository extends MongoRepository<Auto, String> {
}
