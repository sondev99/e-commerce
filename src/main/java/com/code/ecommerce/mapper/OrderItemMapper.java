package com.code.ecommerce.mapper;


import com.code.ecommerce.dto.request.OrderItemRequest;
import com.code.ecommerce.dto.response.OrderItemDto;
import com.code.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItemDto, OrderItem> {

    OrderItem reqToEntity(OrderItemRequest orderItemRequest);

    Set<OrderItem> reqToEntity(Set<OrderItemRequest> orderItemRequests);

}