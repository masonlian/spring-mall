package com.masonlian.springmall.dao.impl;

import com.masonlian.springmall.dao.ProductDao;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.rowmapper.ProductRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product  getByproductId(Integer productId) {
        String sql = "SELECT  product_id, product_name, category, image_url, price, stock, description, created_date," +
                " last_modified_date FROM product WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowmapper());

        if (productList.size() >0) {
            return productList.get(0);
        }
        else return null;
    }






}



