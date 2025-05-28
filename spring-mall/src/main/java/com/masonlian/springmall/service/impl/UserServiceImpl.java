package com.masonlian.springmall.service.impl;

import com.masonlian.springmall.dao.UserDao;
import com.masonlian.springmall.dto.UserLogInRequest;
import com.masonlian.springmall.dto.UserRegisterRequest;
import com.masonlian.springmall.model.User;
import com.masonlian.springmall.service.UserService;
import com.masonlian.springmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final  static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest){

        String email = userRegisterRequest.getEmail();
        User user = userDao.getUserByEmail(email);

        if(user!=null){
            log.warn("該電子信箱{}已被註冊",email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"<UNK>");
        }

        String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashPassword);

        return userDao.createUser(userRegisterRequest);
    }


    public User  getUserById(Integer userId){

        return userDao.getUserById(userId);

    }


    //把驗證傳入的email userregisterRequest與資料庫中的數據做比對 邏輯運作在service這層當中
    public User logIn(UserLogInRequest userLogInRequest ) {

        User user = userDao.getUserByEmail(userLogInRequest.getEmail());//因為是用email去取得所以如果回傳空值就代表email尚未被註冊一個步驟

        if (user == null) {
            log.warn("該驗證信箱{}尚未註冊",userLogInRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword().equals(userLogInRequest.getPassword())) {
            return user;
        }
        else {
            log.warn("該組密碼 {}不正確",userLogInRequest.getPassword());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }



}

