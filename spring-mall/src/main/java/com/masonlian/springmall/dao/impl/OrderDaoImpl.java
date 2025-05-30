package com.masonlian.springmall.dao.impl;

import com.masonlian.springmall.dao.OrderDao;
import com.masonlian.springmall.dto.CreateOrderRequest;
import com.masonlian.springmall.dto.OrderQueryRequest;
import com.masonlian.springmall.model.Order;
import com.masonlian.springmall.model.OrderItem;
import com.masonlian.springmall.rowmapper.OrderItemRowMapper;
import com.masonlian.springmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Integer createOrder(Integer userId ,Integer totalAmount ){

        String sql= "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUE  (:user_id, :total_amount, :created_date, :last_modified_date) ";
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("total_amount",totalAmount);
        Date date = new Date();
        map.put("created_date",date);
        map.put("last_modified_date",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer orderId =keyHolder.getKey().intValue();
        return orderId;
    };

    public void createOrderItem (Integer orderId, List<OrderItem> orderItemList){
//        for( OrderItem orderItem:orderItemList) {
//            String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (:order_id, :product_id, :quantity, :amount) ";
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("order_id", orderId);
//            map.put("product_id", orderItem.getProductId());
//            map.put("quantity", orderItem.getQuantity());
//            map.put("amount", orderItem.getAmount());
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//            Integer orderItemId = keyHolder.getKey().intValue();
//        }

        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (:order_id, :product_id, :quantity, :amount) ";
         // Map<String,Object> map = new HashMap<>();
        MapSqlParameterSource [] mapSqlParameterSource = new MapSqlParameterSource[orderItemList.size()];
        for (int i=0 ; i<orderItemList.size() ; i++) {

            OrderItem orderItem = orderItemList.get(i);

            mapSqlParameterSource[i] = new MapSqlParameterSource();
            mapSqlParameterSource[i].addValue("order_id", orderId);
            mapSqlParameterSource[i].addValue("product_id", orderItem.getProductId());
            mapSqlParameterSource[i].addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSource[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,mapSqlParameterSource);

        }

    public List<OrderItem> getOrderItemsById(Integer orderId){

        String sql =" SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url"+
                " FROM order_item as oi"+
                " LEFT JOIN product as p ON oi.product_id=p.product_id"+
                " WHERE oi.order_id = :order_id" ;// 在product_id上實行table的結合。

        Map<String, Object> map = new HashMap<>();
        map.put("order_id",orderId);
        List<OrderItem> orderItemList= namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

        return orderItemList;


    }

    public Order getOrderById(Integer orderId){
        String sql="SELECT order_id, user_id, total_amount, created_date, last_modified_date  FROM `order` WHERE order_id = :order_id";

        Map<String,Object> map = new HashMap<>();
        System.out.println("orderId = " + orderId);
        map.put("order_id",orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        if (orderList.size()>0){
            return orderList.get(0);
        }else return null;

    }

    public List<Order> getOrders(OrderQueryRequest orderQueryRequest){
        String sql= " SELECT order_id,user_id,created_date, last_modified_date  FROM `order` WHERE 1=1 ";
        Map<String,Object> map = new HashMap<>();
        addFilteringSql(sql,map,orderQueryRequest);
        sql= sql+ "ORDER BY created_date DESC";

        sql=sql + "LIMIT :limit OFFSET :offset";
        map.put("limit",orderQueryRequest.getLimit());
        map.put("offset",orderQueryRequest.getOffset());

       List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
       return orderList;

    }

    public Integer countOrders(OrderQueryRequest orderQueryRequest){

        String sql=" SELECT count(*) FROM `order` WHERE 1=1";
        Map<String,Object> map = new HashMap<>();
        addFilteringSql(sql,map,orderQueryRequest);

        Integer count =namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return count;









    }

    public String addFilteringSql(String sql,Map<String,Object> map,OrderQueryRequest orderQueryRequest){

        if(orderQueryRequest.getUserId()!=null)
        { sql=sql+  "AND user_id = :user_id ";
            map.put("user_id", orderQueryRequest.getUserId());
            return sql;
        }
            else return null;
    }









}




