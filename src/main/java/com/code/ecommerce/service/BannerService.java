package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.BannerRequest;
import com.code.ecommerce.dto.response.BannerDto;
import com.code.ecommerce.dto.response.PagingData;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    String create(BannerRequest bannerRequest);

    BannerDto findById(String id);

    PagingData findByCondition(String searchText, Integer offset, Integer pageSize, String sortStr);

    BannerDto update(MultipartFile file, String data, String id) throws JsonProcessingException;

    String deleteById(String id);

}
