package com.example.currencyconverter.service.response;


import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ExchangeQuery {

    private String from;
    private String to;
    private int amount;

}
