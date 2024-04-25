package com.code.ecommerce.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String status;
}
