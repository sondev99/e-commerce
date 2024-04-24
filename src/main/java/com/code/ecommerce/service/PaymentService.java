package com.code.ecommerce.service;


import com.code.ecommerce.dto.request.PaymentRequest;
import com.code.ecommerce.dto.response.PagingData;
import com.code.ecommerce.dto.response.PaymentDto;
import com.stripe.exception.StripeException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PaymentService {

    String checkout(PaymentRequest paymentRequest, String token)
            throws UnsupportedEncodingException, StripeException;

    PaymentDto findById(String id);

    PagingData getPayments(Integer offset, Integer pageSize, String sortStr);

    PaymentDto updatePayment(Map<String, Object> fields, String id);

    String deleteById(String id);

}
