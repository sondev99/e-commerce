package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.RatingRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.RatingDto;

public interface RatingService {

    RatingDto create(RatingRequest ratingRequest);

    PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId);

    PagingData getByProduct(Integer offset, Integer pageSize, String productId);

    RatingDto update(String id, RatingRequest ratingRequest, String token);

    String delete(String id);

}
