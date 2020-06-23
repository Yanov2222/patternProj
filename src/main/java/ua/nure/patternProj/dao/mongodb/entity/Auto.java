package ua.nure.patternProj.dao.mongodb.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Auto {
    @Id
    private String id;
    private Integer seats;
    private String model;
    private Integer price;
    private Integer hasBabySeat;
    private Integer hasConditioner;
    private Integer hasBar;
    private String manufacturer;
    @Indexed
    private String uuid;
}
