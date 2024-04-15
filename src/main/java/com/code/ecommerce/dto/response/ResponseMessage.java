package com.code.ecommerce.dto.response;


import com.code.ecommerce.constance.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {
    private ResponseStatus status;
    private String message;
    private Object data;

}
