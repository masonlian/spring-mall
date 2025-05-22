package com.masonlian.springmall.controller;

import com.masonlian.springmall.dao.ProductDao;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductorController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId)
    {

        Product product = productService.getProductById((productId));

       if (product != null)
       {return ResponseEntity.status(HttpStatus.OK).body(product);}
       else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

       Integer productId =  productService.createProduct(productRequest);
       Product product = productService.getProductById(productId);
       return ResponseEntity.status(HttpStatus.CREATED).body(product);





    }





}
