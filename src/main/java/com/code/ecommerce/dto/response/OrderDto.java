package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import com.code.ecommerce.entity.DeliveryStatus;
import com.code.ecommerce.entity.OrderItem;
import com.code.ecommerce.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends AbstractDto<String> {

    private String id;

    private Date orderDate;

    private double totalPrice;

    private Status status;

    private DeliveryStatus deliveryStatus;

    private String userId;

    private List<OrderItem> orderItems;
}