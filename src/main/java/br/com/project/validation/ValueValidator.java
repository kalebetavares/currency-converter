package br.com.project.validation;

import br.com.project.exception.InvalidValueException;

import java.math.BigDecimal;

public class ValueValidator {

    public void validateValue(BigDecimal value) {
        if (value == null) {
            throw new InvalidValueException("Valor inválido: o valor informado é nulo.");
        }

        if (value.compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidValueException("Valor inválido: o valor não pode ser zero para conversão monetária.");
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidValueException("Valor inválido: o valor não pode ser negativo para conversão monetária.");
        }
    }
}
