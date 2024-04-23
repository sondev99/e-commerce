package com.code.ecommerce.service;

import com.code.ecommerce.dto.request.ReviewRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ReviewDto;

public interface ReviewService {

    ReviewDto create(ReviewRequest ratingRequest);

    PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId);

    PagingData getByProduct(Integer offset, Integer pageSize, String productId);

    ReviewDto update(String id, ReviewRequest ratingRequest, String token);

    String delete(String id);

}
