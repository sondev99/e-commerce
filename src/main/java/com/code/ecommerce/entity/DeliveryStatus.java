package com.code.ecommerce.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryStatus {

    PENDING("PENDING"),
    DISPATCHED("DISPATCHED"),
    DELIVERED("DELIVERED")
    ;

    private final String status;

}