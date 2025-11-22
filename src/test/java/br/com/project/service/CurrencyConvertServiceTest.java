package br.com.project.service;

import br.com.project.exception.InvalidCurrencyPairException;
import br.com.project.exception.InvalidValueException;
import br.com.project.infrastructure.client.ExchangeRateClient;
import br.com.project.validation.CurrencyPairValidator;
import br.com.project.validation.ValueValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConvertServiceTest {
    private CurrencyConvertService currencyConvertService;
    @Mock
    private ExchangeRateClient exchangeRateClient;
    @Mock
    private ValueValidator valueValidator;
    @Mock
    private CurrencyPairValidator currencyPairValidator;

    @BeforeEach
    void setUp() {
        currencyConvertService = new CurrencyConvertService(valueValidator, currencyPairValidator, exchangeRateClient);
    }

    @Test
    void shouldCallValueValidatorWhenConverting() {
        BigDecimal value = BigDecimal.valueOf(100.00);
        String currencyPair = "USD-BRL";
        BigDecimal returnTax = BigDecimal.valueOf(5.4021);

        when(exchangeRateClient.getExchangeRate(currencyPair))
                .thenReturn(returnTax);

        currencyConvertService.convert(value, currencyPair);

        verify(valueValidator).validateValue(value);
    }

    @Test
    void shouldCallCurrencyPairValidatorWhenConverting() {
        BigDecimal value = BigDecimal.valueOf(50.00);
        String currencyPair = "EUR-BRL";
        BigDecimal returnTax = BigDecimal.valueOf(6.1125);

        when(exchangeRateClient.getExchangeRate(currencyPair))
                .thenReturn(returnTax);

        currencyConvertService.convert(value, currencyPair);

        verify(currencyPairValidator).validateCurrencyPair(currencyPair);
    }

    @Test
    void shouldThrowExceptionWhenValueIsInvalid() {
        BigDecimal negativeValue = BigDecimal.valueOf(-25.00);
        String currencyPair = "BRL-EUR";

        Mockito.doThrow(new InvalidValueException("Valor inválido: o valor não pode ser negativo para conversão monetária."))
                .when(valueValidator).validateValue(negativeValue);

        assertThrows(InvalidValueException.class, () -> currencyConvertService.convert(negativeValue, currencyPair));

        verify(valueValidator).validateValue(negativeValue);

        Mockito.verifyNoInteractions(exchangeRateClient);
    }

    @Test
    void shouldThrowExceptionWhenCurrencyPairIsInvalid() {
        BigDecimal value = BigDecimal.valueOf(500.00);
        String currencyPairInvalid = "BTC-USD";

        Mockito.doThrow(new InvalidCurrencyPairException("Moeda inválida: somente os pares BRL, USD e EUR são aceitos."))
                .when(currencyPairValidator).validateCurrencyPair(currencyPairInvalid);

        assertThrows(InvalidCurrencyPairException.class, () -> currencyConvertService.convert(value, currencyPairInvalid));

        verify(currencyPairValidator).validateCurrencyPair(currencyPairInvalid);

        Mockito.verifyNoInteractions(exchangeRateClient);
    }

    @Test
    void shouldReturnCorrectlyConvertedValueWhenInputIsValid() {
        BigDecimal valueValid = BigDecimal.valueOf(1000.00);
        String currencyPairValid = "USD-BRL";
        BigDecimal returnTax = BigDecimal.valueOf(5.4021);

        when(exchangeRateClient.getExchangeRate(currencyPairValid))
                .thenReturn(returnTax);

        BigDecimal result = currencyConvertService.convert(valueValid, currencyPairValid);

        BigDecimal expected = BigDecimal.valueOf(5402.10).setScale(2, RoundingMode.HALF_UP);
        assertEquals(expected, result);

        verify(valueValidator).validateValue(valueValid);
        verify(currencyPairValidator).validateCurrencyPair(currencyPairValid);
        verify(exchangeRateClient).getExchangeRate(currencyPairValid);
    }
}