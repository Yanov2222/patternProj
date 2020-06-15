package ua.nure.patternProj.dao.mysql.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Role {
    private int id;
    private String roleName;

    public static RoleBuilder builder(){
        return new RoleBuilder();
    }

    public static class RoleBuilder{
        private Role role;

        public RoleBuilder addId(int id){
            role.id = id;
            return this;
        }
        public RoleBuilder addRoleName(String roleName){
            role.roleName = roleName;
            return this;
        }
        public Role build(){
            return role;
        }
    }
}
