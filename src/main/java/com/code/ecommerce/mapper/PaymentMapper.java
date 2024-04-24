package com.code.ecommerce.mapper;


import com.code.ecommerce.dto.request.PaymentRequest;
import com.code.ecommerce.dto.response.PaymentDto;
import com.code.ecommerce.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDto, Payment> {
    Payment reqToEntity(PaymentRequest paymentRequest);

}