package ua.nure.patternProj.dao.mysql.entity;

import lombok.Data;

@Data
public class Greeting {
    private final long id;
    private final String content;
}
