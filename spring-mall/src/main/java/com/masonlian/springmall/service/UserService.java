package com.masonlian.springmall.service;

import com.masonlian.springmall.dto.UserLogInRequest;
import com.masonlian.springmall.dto.UserRegisterRequest;
import com.masonlian.springmall.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
    User logIn(UserLogInRequest userLogInRequest );

}
