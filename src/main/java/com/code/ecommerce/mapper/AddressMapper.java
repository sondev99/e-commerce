package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.AddressRequest;
import com.code.ecommerce.dto.response.AddressDto;
import com.code.ecommerce.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDto, Address>{
    Address reqToEntity(AddressRequest addressRequest);
}
