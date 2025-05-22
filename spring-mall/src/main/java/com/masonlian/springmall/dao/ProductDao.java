package com.masonlian.springmall.dao;

import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductDao {

    public Product getByproductId(Integer productId);
    public Integer createProduct(ProductRequest productRequest);
    public  void updateProduct(Integer productId, ProductRequest productRequest);
    public void deleteProductById(Integer productId);
}
