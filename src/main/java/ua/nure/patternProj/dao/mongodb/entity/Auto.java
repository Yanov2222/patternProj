package ua.nure.patternProj.dao.mongodb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "auto")
public class Auto {
    @Id
    private String id;
    private int seats;
    private String model;
    private int price;
    private int hasBabySeat;
    private int hasConditioner;
    private int hasBar;
    private String manufacturer;
    @Indexed
    private String uuid;
}
