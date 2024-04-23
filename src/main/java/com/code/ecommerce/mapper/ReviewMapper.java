package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.response.ReviewDto;
import com.code.ecommerce.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDto, Review>{

}