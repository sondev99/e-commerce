package com.code.ecommerce.controller;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.PaymentRequest;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.PaymentService;
import com.stripe.exception.StripeException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping("/checkout")
  public ResponseEntity<ResponseMessage> checkout(@RequestBody @Validated PaymentRequest paymentRequest,
      @RequestHeader("Authorization") String token)
      throws UnsupportedEncodingException, StripeException {

    if (paymentService.checkout(paymentRequest,token) == null) {
      return ResponseEntity.internalServerError().body(new ResponseMessage(
          ResponseStatus.OK,
          "Some error happened went create payment !!",
          paymentService.checkout(paymentRequest, token)));
    }
    return ResponseEntity.ok().body(new ResponseMessage(
        ResponseStatus.OK,
        "Create payment successful !!",
        paymentService.checkout(paymentRequest, token)));
  }
}
