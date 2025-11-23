package br.com.project.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DefaultMoneyFormatter implements MoneyFormatter {

    @Override
    public BigDecimal format(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
