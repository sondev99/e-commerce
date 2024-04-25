package com.code.ecommerce.dto.request;


import com.code.ecommerce.dto.response.OrderItemDto;
import com.code.ecommerce.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {

    @NotNull
    private String deliveryAddress;

    private String  paymentId;

    private double totalPrice;

    private Status status;

    private String userId;

    private List<OrderItemDto> orderItemRequest;

}