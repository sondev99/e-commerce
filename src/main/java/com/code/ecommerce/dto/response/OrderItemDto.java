package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto extends AbstractDto<String> {
    private String id;

    private Integer quantity;

    private String productId;

    private String userId;

}
