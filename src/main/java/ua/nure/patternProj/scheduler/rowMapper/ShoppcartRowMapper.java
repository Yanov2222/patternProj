package ua.nure.patternProj.scheduler.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import ua.nure.patternProj.dao.mysql.entity.ShopCart;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppcartRowMapper implements RowMapper<ShopCart> {

    @Override
    public ShopCart mapRow(ResultSet resultSet, int i) throws SQLException {
        ShopCart shopCart = new ShopCart();
        while (resultSet.next()){
            shopCart.setIdOrder(resultSet.getInt("orders_id"));

        }
        return shopCart;
    }
}
