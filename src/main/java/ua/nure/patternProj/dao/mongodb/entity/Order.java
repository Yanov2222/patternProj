package ua.nure.patternProj.dao.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private Date orderingDate;
    private double sum;
    private String address;
    @DBRef
    private User user;
    @DBRef
    private Collection<Auto> autos;
    @Indexed
    private String uuid;
}
