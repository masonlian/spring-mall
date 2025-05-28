package com.masonlian.springmall.dao;

import com.masonlian.springmall.dto.UserLogInRequest;
import com.masonlian.springmall.dto.UserRegisterRequest;
import com.masonlian.springmall.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    public User getUserById(Integer userid);
    public Integer createUser(UserRegisterRequest userRegisterRequest);
    public User getUserByEmail(String email);
}
