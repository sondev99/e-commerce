package com.code.ecommerce.dto.response;

import com.code.ecommerce.dto.AbstractDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto extends AbstractDto<String> {

    private String id;
    private String name;
    private String imageUrl;

}