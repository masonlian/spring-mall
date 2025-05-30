package com.masonlian.springmall.controller;

import com.masonlian.springmall.dto.CreateOrderRequest;
import com.masonlian.springmall.dto.OrderQueryRequest;
import com.masonlian.springmall.model.Order;
import com.masonlian.springmall.service.OrderService;
import com.masonlian.springmall.service.UserService;
import com.masonlian.springmall.util.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @PostMapping("/user/{userId}/order")//創建在在userId之下的order頁面
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId ,
                                         @RequestBody CreateOrderRequest createOrderRequest) {

        System.out.println("userId = " + userId);
        System.out.println("buyItemList = " + createOrderRequest.getBuyItemList());

        Integer orderId = orderService.createOrder(userId ,createOrderRequest );

        Order order =orderService.getOrderById(orderId);  //這編的訂單資訊是一筆訂單裡面包含多少品項

        return ResponseEntity.status(HttpStatus.OK).body(order);

    }

    @GetMapping("/order/{orderId}/orders")// 這邊是取得單名使用者有哪些訂單
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
    @RequestParam( defaultValue ="10") @Max(100)@Min(0) Integer limit,
    @RequestParam( defaultValue = "0") @Max(0)Integer offset) {

        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setUserId(userId);
        orderQueryRequest.setOffset(offset);
        orderQueryRequest.setLimit(limit);

        List<Order> OrderQuerylist = orderService.getOrders(orderQueryRequest);

        Integer count = orderService.countOrders(orderQueryRequest);

        Page orderPage = new Page();
        orderPage.setTotal(count);
        orderPage.setLimit(limit);
        orderPage.setOffset(offset);
        orderPage.setResult(OrderQuerylist);

        return ResponseEntity.status(HttpStatus.OK).body(orderPage);


    }

}
