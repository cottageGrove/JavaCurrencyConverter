package com.example.currencyconverter.service.response;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ExchangeResponse {

    ExchangeQuery query;
    boolean success;
    float result;

}
