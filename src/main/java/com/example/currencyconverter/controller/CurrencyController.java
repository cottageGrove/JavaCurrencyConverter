package com.example.currencyconverter.controller;

import com.example.currencyconverter.exceptions.ApiRequestException;
import com.example.currencyconverter.exceptions.ErrorDetail;
import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.ExchangeRateService;
import com.example.currencyconverter.service.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/convertValue")
    public ResponseEntity getConvertedCurrency(@RequestParam String sourceCurrency,
                                         @RequestParam String targetCurrency,
                                         @RequestParam int monetaryValue) {

        //check whether params are empty, perform validation
        Currency currency = new Currency(sourceCurrency, targetCurrency, monetaryValue);

        //Make api call to validate symbols
        if(!exchangeRateService.getSupportedCurrencySymbols(currency)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetail(HttpStatus.BAD_REQUEST.toString(), "Invalid currency symbols specified"));
        };

        //Check whether source, target, amount exists in mongo before making api call to exchangeRatesApi
        ExchangeResponse existingCurrency = exchangeRateService.getExistingCurrency(currency);

        if(existingCurrency != null)
            return ResponseEntity.ok(existingCurrency);

        ExchangeResponse response = exchangeRateService.getExchange(currency);
        return ResponseEntity.ok(response);
    }

}
