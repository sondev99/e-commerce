package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.BrandRequest;
import com.code.ecommerce.dto.response.BrandDto;
import com.code.ecommerce.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDto, Brand>{

    Brand reqToEntity(BrandRequest brandRequest);

}