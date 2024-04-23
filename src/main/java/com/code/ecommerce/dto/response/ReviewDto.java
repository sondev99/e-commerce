package com.code.ecommerce.dto.response;


import com.code.ecommerce.dto.AbstractDto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReviewDto extends AbstractDto<String> {

    private String id;

    private Double rate;

    private String content;

    private String userId;

    private ProductDto product;

    private Date createDate;
}