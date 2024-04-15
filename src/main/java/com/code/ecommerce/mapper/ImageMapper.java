package com.code.ecommerce.mapper;


import com.code.ecommerce.dto.request.ImageRequest;
import com.code.ecommerce.dto.response.ImageDto;
import com.code.ecommerce.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDto, Image>{

    Image reqToEntity(ImageRequest imageRequest);

}