package com.masonlian.springmall.dao.impl;

import com.masonlian.springmall.dao.UserDao;
import com.masonlian.springmall.dto.UserLogInRequest;
import com.masonlian.springmall.dto.UserRegisterRequest;
import com.masonlian.springmall.model.User;
import com.masonlian.springmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Integer createUser(UserRegisterRequest userRegisterRequest){

        String sql=" INSERT INTO user( email,password,created_date,last_modified_date) VALUES (:email,:password,:created_date,:last_modified_date) ";
        Map<String,Object> map=new HashMap<>();

        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date date = new Date();

        map.put("created_date",date);
        map.put("last_modified_date",date);

        KeyHolder keyHolder=new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder );

        int userId= keyHolder.getKey().intValue();


        return userId;


    }; //在概念上把create這件事跟register分開，讓create為dao層專有的操作

    public User getUserById(Integer userId){

        String sql = "SELECT user_id,email,password,created_date,last_modified_date FROM user WHERE user_id=:userid ";

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);

        System.out.println("userId為"+userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map, new UserRowMapper());

        //有了UserRowMapper() 才能夠將SQL的語法轉乘java物件 "user_id=?"
        if(userList.size()>0)
            return userList.get(0);
        return null;
    }

    //驗證傳入的數據是否存在於資料庫中
    public User getUserByEmail(String email){

        String sql =" SELECT email,password FROM user WHERE email=:email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);

        List<User> userList = namedParameterJdbcTemplate.query(sql,map, new UserRowMapper());
        if(userList.size()>0)
            return userList.get(0);
        else return null;
    };









    };//去跟資料庫做互動。



