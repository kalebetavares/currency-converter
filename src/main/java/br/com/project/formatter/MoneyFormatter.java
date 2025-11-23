package br.com.project.formatter;

import java.math.BigDecimal;

public interface MoneyFormatter {

    BigDecimal format(BigDecimal value);
}
