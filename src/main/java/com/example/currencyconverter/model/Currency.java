package com.example.currencyconverter.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Currency {

    private String sourceCurrency;
    private String targetCurrency;
    private int monetaryValue;

    public Currency(String sourceCurrency, String targetCurrency, int monetaryValue) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.monetaryValue = monetaryValue;
    }
}
