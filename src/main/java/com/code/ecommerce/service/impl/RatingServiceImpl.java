package com.code.ecommerce.service.impl;

import com.code.ecommerce.dto.request.RatingRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.RatingDto;
import com.code.ecommerce.entity.Rating;
import com.code.ecommerce.exceptions.APIException;
import com.code.ecommerce.exceptions.NotFoundException;
import com.code.ecommerce.mapper.RatingMapper;
import com.code.ecommerce.repository.ImageRepository;
import com.code.ecommerce.repository.ProductRepository;
import com.code.ecommerce.repository.RatingRepository;
import com.code.ecommerce.service.RatingService;
import com.code.ecommerce.utils.JwtUtils;
import io.jsonwebtoken.Claims;
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
public class RatingServiceImpl implements RatingService {

    private final ImageRepository imageRepository;
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final RatingMapper ratingMapper;


    @Override
    public RatingDto create(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .content(ratingRequest.getContent())
                .product(productRepository.findById(ratingRequest.getProductId()).orElseThrow(
                        () -> new NotFoundException("Can't find product with id" + ratingRequest.getProductId())))
                .userId(ratingRequest.getUserId())
                .build();
        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    @Override
    public PagingData getByProductAndUser(Integer offset, Integer pageSize, String productId, String userId) {
        Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Rating> evaluatePage = ratingRepository.findByProductIdAndUserId(pageable, productId, userId);

        Page<RatingDto> evaluateDtoPage = evaluatePage.map(ratingMapper::toDto);

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

        Page<Rating> evaluatePage = ratingRepository.findByProductId(pageable, productId);
//        UserDto user = getUserById(evaluatePage.getContent())

        Page<RatingDto> evaluateDtoPage = evaluatePage.map(ratingMapper::toDto);

        return PagingData.builder()
                .data(evaluateDtoPage.getContent())
                .offset(offset)
                .pageSize(pageSize)
                .totalRecord(evaluateDtoPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public RatingDto update(String id, RatingRequest ratingRequest, String token) {
        Claims claims = JwtUtils.parseClaims(token);
        String currentUserId = (String) claims.get("userId");

        if (!StringUtils.equals(ratingRequest.getUserId(), currentUserId)) {
            throw new APIException("You cannot evaluate as another user");
        }

        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find evaluate with id" + id));
        rating.setContent(ratingRequest.getContent());

        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    @Override
    public String delete(String id) {
        ratingRepository.deleteById(id);
        return id;
    }


}
