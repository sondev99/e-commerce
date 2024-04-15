package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.CategoryRequest;
import com.code.ecommerce.dto.response.CategoryDto;
import com.code.ecommerce.dto.response.PagingData;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    String createCategory(CategoryRequest categoryRequest);

    PagingData getCategories(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDto findCategoryById(String id);

    List<CategoryDto> getBaseCategories();

    CategoryDto updateCategory(MultipartFile file, MultipartFile iconFile, String data, String id)
            throws JsonProcessingException, IllegalAccessException;

    String deleteCategoryById(String id);

    List<CategoryDto> getAll();

}
