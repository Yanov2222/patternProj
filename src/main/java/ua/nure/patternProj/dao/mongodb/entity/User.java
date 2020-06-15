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
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed
    private String uuid;
    private String login;
    private String password;
    private String email;
    private String name;
    private String telephone;
    private String role;
}
