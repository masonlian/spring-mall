package com.masonlian.springmall.dao.impl;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.dao.ProductDao;
import com.masonlian.springmall.dto.ProductQueryPara;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.rowmapper.ProductRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getByproductId(Integer productId) {
        String sql = "SELECT  product_id, product_name, category, image_url, price, stock, description, created_date," +
                " last_modified_date FROM product WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowmapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES(:productName, :category, :imageUrl, :price, :stock, :description,:createdDate,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();

        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = keyHolder.getKey().intValue();

        return productId;


    }

    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_Name=:productName,category=:category,image_url=:imageUrl,price=:price,stock=:stock,description=:description,last_modified_date=:lastModifiedDate WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("lastModifiedDate", now);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);

    }

    ;

    @Override
    public List<Product> getProduct(ProductQueryPara productQueryPara) {


        String sql = "SELECT  product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        addFilteringSql(sql, map, productQueryPara); //透過用method去統一整理sql的操作，定義好getprodcut這個階層，在把對query的“動作封裝在同一個方法之中。

        sql = sql + " ORDER BY " + productQueryPara.getOrderBy() + " " + productQueryPara.getSort();

        List<Product> productsList = namedParameterJdbcTemplate.query(sql, map, new ProductRowmapper());

        return productsList;

    }


    private String addFilteringSql(String sql,Map<String,Object> map, ProductQueryPara productQueryPara) {
        if (productQueryPara.getCategory() != null) {
            sql = sql + " AND category=:category";
            map.put("category", productQueryPara.getCategory().name());
        }
        if (productQueryPara.getSearch() != null)
            sql = sql + " AND product_name LIKE :search";
        map.put("search", "%" + productQueryPara.getSearch() + "%");

        return sql;
    }
}
    //ProductrowMapper會將參數傳入sql中}







