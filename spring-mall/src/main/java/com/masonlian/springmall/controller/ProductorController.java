package com.masonlian.springmall.controller;

import com.masonlian.springmall.constant.ProductCategory;
import com.masonlian.springmall.dto.ProductQueryPara;
import com.masonlian.springmall.dto.ProductRequest;
import com.masonlian.springmall.model.Product;
import com.masonlian.springmall.service.ProductService;
import com.masonlian.springmall.util.Page;
import jakarta.validation.ParameterNameProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class ProductorController {

    @Autowired
    private ProductService productService;






    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct
            (@RequestParam (required=false)ProductCategory category,
             @RequestParam (required=false) String search,

             @RequestParam (defaultValue = "created_date")String orderBy,
             @RequestParam (defaultValue = "desc")String sort,

             @RequestParam (name = "limit",defaultValue = " 5 ") @Max(100) @Min(0) int limit,
             @RequestParam (defaultValue = " 1 ") @Min(0) Integer offset
            )
    {

        ProductQueryPara productQueryPara = new ProductQueryPara();

        productQueryPara.setCategory(category);
        productQueryPara.setSearch(search);
        productQueryPara.setOrderBy(orderBy);
        productQueryPara.setSort(sort);
        productQueryPara.setLimit(limit);
        productQueryPara.setOffset(offset);

        System.out.println("limit = " + productQueryPara.getLimit());
        System.out.println("offset = " + productQueryPara.getOffset());

        List<Product> prodcutsList = productService.getProduct(productQueryPara);

        Integer total = productService.countProduct(productQueryPara);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(prodcutsList); //page只負責接住操作結果並且回傳參數給前端並不會實際去操作底層SQL

        return ResponseEntity.status(HttpStatus.OK).body(page);


    }

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

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        if(productId==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productService.updateProduct(productId,productRequest); //去更新商品數據
        Product updatedProduct =productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();


}






}
