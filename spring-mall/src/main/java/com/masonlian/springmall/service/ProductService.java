package com.masonlian.springmall.service;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.dto.ProductQueryPara;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.util.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
   Product getProductById(Integer productId);
   Integer createProduct(ProductRequest productRequest);
   public void updateProduct(Integer productId,ProductRequest productRequest);
   public void deleteProductById(Integer productId);

   public List<Product> getProduct(ProductQueryPara productQueryPara);
   public Integer countProduct(ProductQueryPara productQueryPara);
}
