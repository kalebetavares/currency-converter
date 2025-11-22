package br.com.project.validation;

import br.com.project.exception.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValueValidatorTest {
    private ValueValidator valueValidator;

    @BeforeEach
    void setUp() {
        valueValidator = new ValueValidator();
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenValueIsNull() {
        Exception exception = assertThrows(InvalidValueException.class, () -> valueValidator.validateValue(null));

        assertEquals("Valor inválido: o valor informado é nulo.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenNegativeValue() {
        BigDecimal negativeValue = BigDecimal.valueOf(-50.00);

        Exception exception = assertThrows(InvalidValueException.class, () -> valueValidator.validateValue(negativeValue));

        assertEquals("Valor inválido: o valor não pode ser negativo para conversão monetária.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenValueIsZero() {
        BigDecimal value = BigDecimal.ZERO;

        Exception exception = assertThrows(InvalidValueException.class, () -> valueValidator.validateValue(value));

        assertEquals("Valor inválido: o valor não pode ser zero para conversão monetária.", exception.getMessage());
    }

    @Test
    void shouldValidateValueSuccessfully() {
        BigDecimal value = BigDecimal.valueOf(5.4008);

        assertDoesNotThrow(() -> valueValidator.validateValue(value));
    }
}