package com.code.ecommerce.dto.response;

import com.code.ecommerce.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountOrderByStatusResponse {

    private Status status;
    private Integer number;

}
