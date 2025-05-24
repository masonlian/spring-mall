package com.masonlian.springmall.dto;

import com.masonlian.springmall.constant.ProductCategory;

public class ProductQueryPara {
    private ProductCategory category;

    public  ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public  String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

   private String search;

   private String orderBy;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    private  String sort;

}
