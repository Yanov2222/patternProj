package ua.nure.patternProj.dao.mysql.entity;


import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {
    private int id;
    private String uuid;
    private String login;
    private String password;
    private String email;
    private String name;
    private String telephone;
    private int statusId;


    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public static class UserBuilder{

        private User user;

        private UserBuilder() {
            user = new User();
        }

        public UserBuilder addId(int id){
            user.id = id;
            return this;
        }
        public UserBuilder addLogin(String login){
            user.login = login;
            return this;
        }
        public UserBuilder addPassword(String password){
            user.password = password;
            return this;
        }
        public UserBuilder addEmail(String email){
            user.email = email;
            return this;
        }
        public UserBuilder addName(String name){
            user.name = name;
            return this;
        }
        public UserBuilder addTelephone(String telephone){
            user.telephone = telephone;
            return this;
        }
        public UserBuilder addStatusId(int statusId){
            user.statusId = statusId;
            return this;
        }

        public UserBuilder addUuid(String uuid){
            user.setUuid(uuid);
            return this;
        }

        public User build(){
            return user;
        }

    }
}
