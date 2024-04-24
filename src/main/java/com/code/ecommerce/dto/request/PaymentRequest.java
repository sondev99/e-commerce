package com.code.ecommerce.dto.request;

import com.code.ecommerce.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private PaymentMethod paymentMethod;
    private double totalPrice;
    private StripeRequest stripeRequest;
}