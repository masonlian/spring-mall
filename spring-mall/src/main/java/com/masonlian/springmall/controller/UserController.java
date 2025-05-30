package com.masonlian.springmall.controller;

import com.masonlian.springmall.dao.UserDao;
import com.masonlian.springmall.dto.UserLogInRequest;
import com.masonlian.springmall.dto.UserRegisterRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.model.User;
import com.masonlian.springmall.service.ProductService;
import com.masonlian.springmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/register") //註冊新帳號
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        //Dao曾
        Integer userId = userService.register(userRegisterRequest);// user這個物件創建好之後就可以做傳導userId了


        //retrun 創建好的user資料給前端 所以要先構造一個get方法去取得剛post進去的資料，一進一出。
        //函數不是只能夠操作回傳的值，我可以做完操作之後再回傳一個特定的東西，不要被表象給騙了
        User user = userService.getUserById(userId);
        System.out.println("該註冊電子信箱為："+user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

       //實踐log in 的概念 去確認該數是否已在數據庫之中
        @PostMapping("/User/login")
        public ResponseEntity<User> logIn(@RequestBody @Valid UserLogInRequest userLogInRequest ){

           User user= userService.logIn(userLogInRequest );
           return ResponseEntity.status(HttpStatus.OK).body(user);


        }



    }

