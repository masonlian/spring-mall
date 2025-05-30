package com.masonlian.springmall.service;

import com.masonlian.springmall.dto.CreateOrderRequest;
import com.masonlian.springmall.dto.OrderQueryRequest;
import com.masonlian.springmall.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderService {

    public Integer createOrder(Integer userId , CreateOrderRequest createOrderRequest);//createOrderRequest 用於創建order訂單，Integer userId 用於connect user
    public Order getOrderById(Integer orderId);
    public List<Order>  getOrders(OrderQueryRequest orderQueryRequest);
    public Integer countOrders(OrderQueryRequest orderQueryRequest);

}// Service 曾的createOrdert除了要創建資料庫中的order資料之外，還需要創建order_item的資料去建立單筆訂單。