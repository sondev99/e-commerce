package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto extends AbstractDto<String> {
    private String id;
    private String thumbnailUrl;
}