package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.response.ExchangeResponse;

import java.util.List;

public interface CurrencyCustomRepository {

    ExchangeResponse findByConvertedTo(ExchangeResponse response);
    ExchangeResponse findExistingCurrency(Currency currency);
}
