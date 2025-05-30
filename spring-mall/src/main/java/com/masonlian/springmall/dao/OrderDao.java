package com.masonlian.springmall.dao;

import com.masonlian.springmall.dto.CreateOrderRequest;
import com.masonlian.springmall.dto.OrderQueryRequest;
import com.masonlian.springmall.model.Order;
import com.masonlian.springmall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDao {
    public Integer createOrder(Integer userId,Integer totalAmount );
    public void createOrderItem (Integer orderId, List<OrderItem> orderItemList);
    public List<OrderItem> getOrderItemsById(Integer orderId);
    public Order getOrderById(Integer orderId);;
    public List<Order> getOrders(OrderQueryRequest orderQueryRequest);
    public Integer countOrders(OrderQueryRequest orderQueryRequest);

}// Service 層 要建構的create order跟dao 層並不壹樣，dao層是要跟資料庫做互動，我需要思考的是要傳入的值有哪些。
 // 很顯然是user_id 跟 total_amount ，
