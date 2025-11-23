package br.com.project.service;

import br.com.project.formatter.MoneyFormatter;
import br.com.project.infrastructure.client.ExchangeRateClient;
import br.com.project.validation.CurrencyPairValidator;
import br.com.project.validation.ValueValidator;

import java.math.BigDecimal;

public class CurrencyConvertService {
    private final ValueValidator valueValidator;
    private final CurrencyPairValidator currencyPairValidator;
    private final ExchangeRateClient exchangeRateClient;
    private final MoneyFormatter moneyFormatter;

    public CurrencyConvertService(ValueValidator valueValidator, CurrencyPairValidator currencyPairValidator, ExchangeRateClient exchangeRateClient, MoneyFormatter moneyFormatter) {
        this.valueValidator = valueValidator;
        this.currencyPairValidator = currencyPairValidator;
        this.exchangeRateClient = exchangeRateClient;
        this.moneyFormatter = moneyFormatter;
    }

    public BigDecimal convert(BigDecimal value, String currencyPair) {
        valueValidator.validateValue(value);
        currencyPairValidator.validateCurrencyPair(currencyPair);

        BigDecimal exchangeRate = exchangeRateClient.getExchangeRate(currencyPair);

        BigDecimal result = value.multiply(exchangeRate);
        return moneyFormatter.format(result);
    }
}
