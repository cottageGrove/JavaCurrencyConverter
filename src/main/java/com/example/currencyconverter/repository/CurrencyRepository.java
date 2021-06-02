package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.response.ExchangeResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CurrencyRepository extends MongoRepository<ExchangeResponse, String>,  CurrencyCustomRepository {

    public ExchangeResponse findByConvertedTo(ExchangeResponse response);
    public ExchangeResponse findExistingCurrency(Currency currency);

}

