package com.code.ecommerce.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StripeRequest {
    private List<StripeItemRequest> stripeItemList;
    private String orderId;
}
