package br.com.project.validation;

import br.com.project.exception.InvalidCurrencyPairException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyPairValidatorTest {
    private CurrencyPairValidator currencyPairValidator;

    @BeforeEach
    void setUp() {
        currencyPairValidator = new CurrencyPairValidator();
    }

    @Test
    void shouldThrowInvalidCurrencyPairExceptionWhenNull() {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(null));

        assertEquals("Par de moedas inválido: o par informado não pode ser nulo.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n\t"})
    void shoudThrowInvalidCurrencyPairExceptionWhenIsBlank(String currencyPairBlank) {
         Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(currencyPairBlank));

         assertEquals("Moeda inválida: o par de moedas não pode estar em branco.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"USDBRL", "USD--BRL", "USD-BRL-TEST"})
    void shouldThrowInvalidCurrencyPairExceptionForWrongHyphen(String currencyPair) {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(currencyPair));

        assertEquals("Moeda inválida: formato incorreto. Use o padrão XXX-YYY.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "US-BRL",
            "USD-BR",
            "EURO-BRL",
            "BRL-EURR"
    })
    void shouldThrowInvalidCurrencyPairExceptionWhenCodeLengthIsWrong(String invalidPair) {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(invalidPair));

        assertEquals("Moeda inválida: cada moeda deve ter exatamente 3 letras.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usd-BRL",
            "USD-brl",
            "uSd-EUR",
            "BRL-EuR"
    })
    void shouldThrowInvalidCurrencyPairExceptionWhenCodeContainsNonUppercaseLetters(String invalidPair) {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(invalidPair));

        assertEquals("Moeda inválida: cada moeda deve conter apenas letras maiúsculas.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "USD-USD",
            "BRL-BRL",
            "EUR-EUR"
    })
    void shouldThrowInvalidCurrencyPairExceptionWhenBaseAndQuoteAreEqual(String invalidPair) {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(invalidPair));

        assertEquals("Moeda inválida: as duas moedas do par não podem ser iguais.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "BTC-USD",
            "AIR-EUR",
            "JPY-BRL"
    })
    void shouldThrowInvalidCurrencyPairExceptionWhenCurrencyIsNotSupported(String invalidPair) {
        Exception exception = assertThrows(InvalidCurrencyPairException.class, () -> currencyPairValidator.validateCurrencyPair(invalidPair));

        assertEquals("Moeda inválida: somente os pares BRL, USD e EUR são aceitos.", exception.getMessage());
    }

    @Test
    void shouldValidateCurrencyPairSuccessfully() {
        String currencyPair = "USD-BRL";

        assertDoesNotThrow(() -> currencyPairValidator.validateCurrencyPair(currencyPair));
    }
}