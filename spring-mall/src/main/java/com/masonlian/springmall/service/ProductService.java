package com.masonlian.springmall.service;

import com.masonlian.springmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
   Product getProductById(Integer productId);

}
