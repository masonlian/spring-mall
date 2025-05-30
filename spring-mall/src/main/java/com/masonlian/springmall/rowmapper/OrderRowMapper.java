package com.masonlian.springmall.rowmapper;

import com.masonlian.springmall.model.Order;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException{

            Order order =new Order();

            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserId(resultSet.getInt("user_id"));
            order.setTotalAmount(resultSet.getInt("total_amount"));
            order.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
            order.setCreatedDate(resultSet.getTimestamp("created_date"));

            return order;

        }
}
