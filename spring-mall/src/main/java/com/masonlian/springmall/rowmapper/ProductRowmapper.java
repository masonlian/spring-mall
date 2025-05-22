package com.masonlian.springmall.rowmapper;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowmapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Product product =new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        String categoryStr= resultSet.getString("category");
        ProductCategory category = ProductCategory.valueOf(categoryStr);

        product.setCategory(category);
        //將categoryStr 轉換成ProductCategory 的數值


    //將這部分的string參數類別轉換成ProductCategory的型態，今天呈現在我眼前的java都是以物件的形式呈現而非單純的運作邏輯，所以如果今天我想要操作將想操作了邏輯以物件的方式儲存下來，在去做轉換的操作。
        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreateDate(resultSet.getDate("created_date"));
        product.setLastModifiedDate(resultSet.getDate("last_modified_date"));
        return product;

    }
}
