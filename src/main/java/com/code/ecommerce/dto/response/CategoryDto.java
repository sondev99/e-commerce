package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import com.code.ecommerce.entity.Category;
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
public class CategoryDto extends AbstractDto<String> {
    private String id;
    private String name;
    private ImageDto imageUrl;
    private ImageDto iconUrl;
    private Set<ProductDto> products;
    private Category parent;
    private List<Category> children;

}