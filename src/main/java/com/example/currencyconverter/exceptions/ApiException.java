package com.example.currencyconverter.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException {
    private final String message;
    private final String status;

    public ApiException(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
