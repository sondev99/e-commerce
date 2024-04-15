package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.response.RatingDto;
import com.code.ecommerce.entity.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDto, Rating>{

}