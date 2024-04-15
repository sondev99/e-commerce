package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto extends AbstractDto<String> {
    private String id;
    private String name;
    private List<ImageDto> imageUrls;
    private Set<ProductDto> products;
}