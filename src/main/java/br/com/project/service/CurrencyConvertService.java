package br.com.project.service;

import br.com.project.infrastructure.client.ExchangeRateClient;
import br.com.project.validation.CurrencyPairValidator;
import br.com.project.validation.ValueValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConvertService {
    private final ValueValidator valueValidator;
    private final CurrencyPairValidator currencyPairValidator;
    private final ExchangeRateClient exchangeRateClient;

    public CurrencyConvertService(ValueValidator valueValidator, CurrencyPairValidator currencyPairValidator, ExchangeRateClient exchangeRateClient) {
        this.valueValidator = valueValidator;
        this.currencyPairValidator = currencyPairValidator;
        this.exchangeRateClient = exchangeRateClient;
    }

    public BigDecimal convert(BigDecimal value, String currencyPair) {
        valueValidator.validateValue(value);
        currencyPairValidator.validateCurrencyPair(currencyPair);

        BigDecimal exchangeRate = exchangeRateClient.getExchangeRate(currencyPair);

        BigDecimal result = value.multiply(exchangeRate);
        return result.setScale(2, RoundingMode.HALF_UP);
    }
}
