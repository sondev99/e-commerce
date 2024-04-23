package com.code.ecommerce.service.impl;

import com.code.ecommerce.dto.request.ReviewRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.ReviewDto;
import com.code.ecommerce.entity.Review;
import com.code.ecommerce.exceptions.APIException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.ReviewMapper;
import com.code.ecommerce.repository.ProductRepository;
import com.code.ecommerce.repository.ReviewRepository;
import com.code.ecommerce.service.ReviewService;
import com.code.ecommerce.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ProductRepository productRepository;
  private final ReviewMapper reviewMapper;
  private final ObjectMapper objectMapper;


  @Override
  public ReviewDto create(ReviewRequest reviewRequest) {
    Review review = Review.builder()
        .rate(reviewRequest.getRate())
        .content(reviewRequest.getContent())
        .product(productRepository.findById(reviewRequest.getProductId()).orElseThrow(
            () -> new NotFoundException(
                "Can't find product with id" + reviewRequest.getProductId())))
        .userId(reviewRequest.getUserId())
        .createDate(new Date())
        .build();
    return reviewMapper.toDto(reviewRepository.save(review));
  }

  @Override
  public PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId,
      String userId) {
    Pageable pageable = PageRequest.of(offset, pageSize);

    Page<Review> evaluatePage = reviewRepository.findByProductIdAndUserId(pageable, productId,
        userId);

    Page<ReviewDto> evaluateDtoPage = evaluatePage.map(reviewMapper::toDto);

    return PagingData.builder()
        .data(evaluateDtoPage)
        .offset(offset)
        .pageSize(pageSize)
        .totalRecord(evaluateDtoPage.getTotalElements())
        .build();
  }

  @Override
  public PagingData getByProduct(Integer offset, Integer pageSize, String productId) {
    Pageable pageable = PageRequest.of(offset, pageSize);

    Page<Review> evaluatePage = reviewRepository.findByProductId(pageable, productId);
//        UserDto user = getUserById(evaluatePage.getContent())

    Page<ReviewDto> evaluateDtoPage = evaluatePage.map(reviewMapper::toDto);

    return PagingData.builder()
        .data(evaluateDtoPage.getContent())
        .offset(offset)
        .pageSize(pageSize)
        .totalRecord(evaluateDtoPage.getTotalElements())
        .build();
  }

  @Override
  @Transactional
  public ReviewDto update(String id, ReviewRequest reviewRequest, String token) {
    Claims claims = JwtUtils.parseClaims(token);
    String currentUserId = (String) claims.get("userId");

    if (!StringUtils.equals(reviewRequest.getUserId(), currentUserId)) {
      throw new APIException("You cannot evaluate as another user");
    }

    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Can't find evaluate with id" + id));
    review.setContent(reviewRequest.getContent());

    return reviewMapper.toDto(reviewRepository.save(review));
  }

  @Override
  public String delete(String id) {
    reviewRepository.deleteById(id);
    return id;
  }


}
