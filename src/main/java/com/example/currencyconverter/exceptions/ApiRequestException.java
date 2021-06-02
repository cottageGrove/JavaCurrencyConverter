package com.example.currencyconverter.exceptions;

import lombok.*;

@Getter
@Setter
public class ApiRequestException extends RuntimeException {

    private ErrorDetail errorDetail;
    public ApiRequestException(String message) {super(message);}
    public ApiRequestException(String message, Exception e) {super(message, e);}

}
