package br.com.project.exception;

public class InvalidCurrencyPairException extends RuntimeException {

    public InvalidCurrencyPairException(String message) {
        super(message);
    }
}

