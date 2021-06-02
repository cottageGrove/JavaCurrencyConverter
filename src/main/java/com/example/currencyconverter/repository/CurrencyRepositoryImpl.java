package com.example.currencyconverter.repository;

import com.example.currencyconverter.model.Currency;
import com.example.currencyconverter.service.response.ExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import static com.example.currencyconverter.constants.Constants.Fields.*;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public ExchangeResponse findByConvertedTo(ExchangeResponse response) {
        return response;
    }

    public ExchangeResponse findExistingCurrency(Currency currency) {

        ProjectionOperation existingCurrenciesProjection = new ProjectionOperation().and(SOURCE_CURRENCY.toString()).as(SOURCE_CURRENCY.toString())
                                                                                    .and(TARGET_CURRENCY.toString()).as(TARGET_CURRENCY.toString())
                                                                                    .and(AMOUNT.toString()).as(AMOUNT.toString())
                                                                                    .and(SUCCESS.toString()).as(SUCCESS.toString())
                                                                                    .and(RESULT.toString()).as(RESULT.toString());

        MatchOperation matchOperation = Aggregation.match( new Criteria().andOperator(
                                                                            Criteria.where(SOURCE_CURRENCY.toString()).is(currency.getSourceCurrency()),
                                                                            Criteria.where(TARGET_CURRENCY.toString()).is(currency.getTargetCurrency()),
                                                                            Criteria.where(AMOUNT.toString()).is(currency.getMonetaryValue())
                                                                            ));
        Aggregation aggregation = Aggregation.newAggregation(existingCurrenciesProjection, matchOperation);
        List<ExchangeResponse> result = mongoTemplate.aggregate(aggregation,"exchangeResponse", ExchangeResponse.class).getMappedResults();

        return result.isEmpty() ? null : result.get(0);
    }
}
