package ua.nure.patternProj.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import ua.nure.patternProj.dao.DbManager;
import ua.nure.patternProj.dao.IUserDao;
import ua.nure.patternProj.dao.mysql.entity.User;

import java.sql.*;
import java.util.UUID;

@Slf4j
public class UserDao implements IUserDao<User> {

    private static final String READ_USER = "SELECT * FROM users WHERE login = ?";
    private static final String CREATE_USER = "INSERT INTO users(login,password,email,user_name,telephone,status_id) VALUES (?,?,?,?,?,?)";


    @Override
    public boolean create(User obj) {
        obj.setUuid(UUID.randomUUID().toString());
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getLogin());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getEmail());
            statement.setString(4, obj.getName());
            statement.setString(5, obj.getTelephone());
            statement.setInt(6, 1);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                events.notify("create", this);
                return true;
            } else {

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User read(User obj) {
        try {
            Connection con = DbManager.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(READ_USER);
            statement.setString(1, obj.getLogin());
            ResultSet resultSet = statement.executeQuery();
            events.notify("read", this);
            if (!resultSet.next()) {
                return null;
            } else {
                User user = User.builder()
                        .addId(resultSet.getInt("id"))
                        .addLogin(resultSet.getString("login"))
                        .addPassword(resultSet.getString("password"))
                        .addEmail(resultSet.getString("email"))
                        .addName(resultSet.getString("user_name"))
                        .addTelephone(resultSet.getString("telephone"))
                        .addStatusId(resultSet.getInt("status_id"))
                        .build();
                return user;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {

    }
}
