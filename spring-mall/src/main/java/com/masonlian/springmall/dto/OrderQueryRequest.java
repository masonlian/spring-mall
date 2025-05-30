package com.masonlian.springmall.dto;

import jakarta.validation.constraints.NotNull;

public class OrderQueryRequest {

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    @NotNull
    private Integer userId;
    private Integer offset;
    private Integer limit;

}
