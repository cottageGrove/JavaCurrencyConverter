package com.example.currencyconverter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex){
        //Return responseEntity
        ApiException apiRequestException = new ApiException("Unable to retrieve info from api. Please verify the parameters entered are correct",
                                                             HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(apiRequestException, HttpStatus.BAD_REQUEST);
    }
}
