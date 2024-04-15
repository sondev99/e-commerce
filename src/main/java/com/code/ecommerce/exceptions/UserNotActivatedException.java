package com.code.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }

    public UserNotActivatedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
