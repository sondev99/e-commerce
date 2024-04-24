package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import com.code.ecommerce.entity.PaymentMethod;
import com.code.ecommerce.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends AbstractDto<Integer> {

    private String id;
    private String paymentId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;



}