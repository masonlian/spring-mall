package com.masonlian.springmall.dao;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductDao {

    public Product getByproductId(Integer productId);
    public Integer createProduct(ProductRequest productRequest);
    public  void updateProduct(Integer productId, ProductRequest productRequest);
    public void deleteProductById(Integer productId);
    public List<Product> getProductByCategory(ProductCategory category);
}
