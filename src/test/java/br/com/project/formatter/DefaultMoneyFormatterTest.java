package br.com.project.formatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultMoneyFormatterTest {
    private MoneyFormatter moneyFormatter;

    @BeforeEach
    void setUp() {
        moneyFormatter = new DefaultMoneyFormatter();
    }

    @Test
    void shouldRoundDownWhenThirdDecimalIsLessThanFive() {
        BigDecimal input = BigDecimal.valueOf(10.1249);
        BigDecimal expected = BigDecimal.valueOf(10.12).setScale(2, RoundingMode.UNNECESSARY);
        assertEquals(expected, moneyFormatter.format(input));
    }
}