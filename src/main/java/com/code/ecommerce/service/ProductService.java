package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.ProductRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    String createProduct(ProductRequest productRequest);

    ProductDto findProductById(String id);

    PagingData getProducts(String searchText, Integer offset, Integer pageSize, String sortStr);

    PagingData findProductsByCategoryAndBrand(Integer offset, Integer pageSize, String categoryId, String brandId);

    ProductDto updateProduct(List<MultipartFile> files,String data, String id) throws JsonProcessingException;

    String deleteProductById(String id);

    void reduceQuantity(String productId, Integer quantity);

}
