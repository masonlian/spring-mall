package com.masonlian.springmall.service;

import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
   Product getProductById(Integer productId);
   Integer createProduct(ProductRequest productRequest);
   public void updateProduct(Integer productId,ProductRequest productRequest);
   public void deleteProductById(Integer productId);
}
