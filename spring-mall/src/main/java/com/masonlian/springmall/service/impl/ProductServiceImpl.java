package com.masonlian.springmall.service.impl;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.dao.ProductDao;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        System.out.println("資料ID數值為" + productId);
        return productDao.getByproductId(productId);

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);


    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);

    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
    @Override
    public List<Product> getProductByCategory(ProductCategory category){
        return productDao.getProductByCategory(category);

    };

}



