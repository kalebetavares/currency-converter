package br.com.project.infrastructure.client;

import java.math.BigDecimal;

public interface ExchangeRateClient {

    BigDecimal getExchangeRate(String currencyPair);
}
