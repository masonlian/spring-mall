package com.masonlian.springmall.service.impl;

import com.masonlian.springmall.dao.OrderDao;
import com.masonlian.springmall.dao.ProductDao;
import com.masonlian.springmall.dao.UserDao;
import com.masonlian.springmall.dto.BuyItem;
import com.masonlian.springmall.dto.CreateOrderRequest;
import com.masonlian.springmall.dto.OrderQueryRequest;
import com.masonlian.springmall.model.Order;
import com.masonlian.springmall.model.OrderItem;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        if (userId == null) {
            log.warn("此使用者{}並不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0;
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            Product product = productDao.getByproductId(buyItem.getProductId());


            if (product == null) {
                log.warn("商品{}並不存在");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品{}庫存不足");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());


            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;
            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);


            //轉換buyItem進入OrderItem的概念就是
        }


        Integer orderId = orderDao.createOrder(userId, totalAmount); //

        orderDao.createOrderItem(orderId, orderItemList);
        ;

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryRequest orderQueryRequest) {

        List<Order> orderList = orderDao.getOrders(orderQueryRequest);
        for (Order order : orderList) { //order定義在判斷式當中了

            List<OrderItem> orderItemList = orderDao.getOrderItemsById(order.getOrderId());
            order.setOrderItemList(orderItemList);//不要鑽牛腳尖
        }
        return orderList;
    }

    @Override
    public Integer countOrders(OrderQueryRequest orderQueryRequest) {
        if (orderQueryRequest.getUserId() == null) {
            log.warn("該使用者不存在");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }else return orderDao.countOrders(orderQueryRequest);
    }
}
