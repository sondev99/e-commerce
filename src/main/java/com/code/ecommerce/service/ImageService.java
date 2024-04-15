package com.code.ecommerce.service;

import com.code.ecommerce.dto.response.CategoryDto;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;


public interface ImageService {
    Image create(MultipartFile file, String data) throws JsonProcessingException;

    CategoryDto findById(String id);

    PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr);

    CategoryDto update(MultipartFile file, String data, String id);

    String deleteById(String id);
}
