package com.code.ecommerce.exceptions;

public class MissingInputException extends RuntimeException{
    public MissingInputException(String message){
        super(message);
    }
}
