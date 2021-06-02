package com.example.currencyconverter.service;

import com.example.currencyconverter.exceptions.ApiRequestException;
import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.model.Symbol;
import com.example.currencyconverter.repository.CurrencyRepository;
import com.example.currencyconverter.service.response.ExchangeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;


@Service
public class ExchangeRateService {

    @Value("${exchangeRatesApiAccessKey}")
    private String exchangeRatesApiAccessKey;
    private static String exchangeRateApiUrl = "http://api.exchangeratesapi.io/v1";

    private Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
    private final WebClient webClient;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateService(WebClient.Builder webClientBuilder, CurrencyRepository currencyRepository) {
        String baseUrl = exchangeRateApiUrl;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.currencyRepository = currencyRepository;
    }

    public boolean getSupportedCurrencySymbols(Currency currency) {
        try {
            Symbol response =
                    this.webClient.get().uri(uriBuilder -> uriBuilder
                            .path("/symbols")
                            .queryParam("access_key", exchangeRatesApiAccessKey)
                            .build()).retrieve().bodyToMono(Symbol.class).block();

            boolean isSourceCurrencySupported = response.getSymbols().entrySet().stream().anyMatch(s -> s.getKey().equals(currency.getSourceCurrency()));
            boolean isTargetCurrencySupported = response.getSymbols().entrySet().stream().anyMatch(s -> s.getKey().equals(currency.getTargetCurrency()));

            if(isSourceCurrencySupported && isTargetCurrencySupported)
                return true;

            return false;

        } catch(Exception ex) {
            logger.info(ex.getMessage());
            throw new ApiRequestException(ex.getMessage(), ex);
        }
    }

    public ExchangeResponse getExchange(Currency currency) {

        try {
            ExchangeResponse response =
                    this.webClient.get().uri(uriBuilder -> uriBuilder
                            .path("/convert")
                            .queryParam("access_key", exchangeRatesApiAccessKey)
                            .queryParam("from", currency.getSourceCurrency())
                            .queryParam("to", currency.getTargetCurrency())
                            .queryParam("amount", currency.getMonetaryValue())
                            .build()).retrieve().bodyToMono(ExchangeResponse.class).block();

            currencyRepository.save(response);
            return response;
        } catch(Exception ex) {
            logger.info(ex.getMessage());
            throw new ApiRequestException(ex.getMessage(), ex);
        }
    }

    public ExchangeResponse getExistingCurrency(Currency currency) {
        ExchangeResponse existingCurrency = currencyRepository.findExistingCurrency(currency);
        return existingCurrency;
    }
}
