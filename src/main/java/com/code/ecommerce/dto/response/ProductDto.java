package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import com.code.ecommerce.entity.Review;
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
    private Double price;
    private Double salePrice;
    private Integer quantity;
    private Double discount;
    private List<ReviewDto> reviews;

}