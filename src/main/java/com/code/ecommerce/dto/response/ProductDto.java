package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AbstractDto<String> {

    private String id;
    private String name;
    private String description;
    private List<ImageDto> imageUrls;
    private BrandDto brandDTO;
    private CategoryDto categoryDTO;
    private String sku;
    private Double priceUnit;
    private Integer quantity;
    private Integer discount;

}