package com.code.ecommerce.dto.request;

import jakarta.validation.constraints.Size;


public class ProductSearchRequest {
    @Size(max = 500)
    private String searchText;
    private Integer offset;
    private Integer pageSize;
    private String sort;
}
