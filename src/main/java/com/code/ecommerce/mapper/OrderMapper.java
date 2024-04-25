package com.code.ecommerce.mapper;

import com.code.ecommerce.dto.request.OrderRequest;
import com.code.ecommerce.dto.response.OrderDto;
import com.code.ecommerce.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDto, Order> {

    OrderDto toDto(Order order);

    Order reqToEntity(OrderRequest orderRequest);
}