package com.example.currencyconverter.exceptions;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class ErrorDetail implements Serializable {
    private String code;
    private String message;


    public ErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
