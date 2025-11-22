package br.com.project.validation;

import br.com.project.exception.InvalidCurrencyPairException;

import java.util.Set;

public class CurrencyPairValidator {

    private static final Set<String> SUPPORTED_CURRENCIES = Set.of(
            "BRL",
            "USD",
            "EUR"
    );

    public void validateCurrencyPair(String currencyPair) {
        if (currencyPair == null) {
            throw new InvalidCurrencyPairException("Moeda inválida: o par informado não pode ser nulo.");
        }

        if (currencyPair.isBlank()) {
            throw new InvalidCurrencyPairException("Moeda inválida: o par de moedas não pode estar em branco.");
        }

        String trimmedCurrencyPair = currencyPair.trim();

        if (trimmedCurrencyPair.chars().filter(character -> character == '-').count() != 1) {
            throw new InvalidCurrencyPairException("Moeda inválida: formato incorreto. Use o padrão XXX-YYY.");
        }

        String[] parts = trimmedCurrencyPair.split("-");
        String base = parts[0];
        String quote = parts[1];

        if (base.length() != 3 || quote.length() != 3) {
            throw new InvalidCurrencyPairException("Moeda inválida: cada moeda deve ter exatamente 3 letras.");
        }

        if (!base.matches("[A-Z]+") || !quote.matches("[A-Z]+")) {
            throw new InvalidCurrencyPairException("Moeda inválida: cada moeda deve conter apenas letras maiúsculas.");
        }

        if (base.equals(quote)) {
            throw new InvalidCurrencyPairException("Moeda inválida: as duas moedas do par não podem ser iguais.");
        }

        if (!SUPPORTED_CURRENCIES.contains(base) || !SUPPORTED_CURRENCIES.contains(quote)) {
            throw new InvalidCurrencyPairException("Moeda inválida: somente os pares BRL, USD e EUR são aceitos.");
        }
    }
}
