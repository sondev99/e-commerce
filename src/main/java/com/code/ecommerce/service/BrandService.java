package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.BrandRequest;
import com.code.ecommerce.dto.response.BrandDto;
import com.code.ecommerce.dto.response.PagingData;

import java.util.List;
import java.util.Map;

public interface BrandService {

    String create(BrandRequest brandRequest);

    PagingData getBrands(String searchText, Integer offset, Integer pageSize, String sortStr);

    BrandDto findById(String id);

    BrandDto update(Map<String, Object> fields, String id);

    String deleteById(String id);

    List<BrandDto> getAll();
}
