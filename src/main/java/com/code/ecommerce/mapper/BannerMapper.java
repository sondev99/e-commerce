package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.BannerRequest;
import com.code.ecommerce.dto.response.BannerDto;
import com.code.ecommerce.entity.Banner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper extends EntityMapper<BannerDto, Banner>{

    Banner reqToEntity(BannerRequest bannerRequest);

}