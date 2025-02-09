package com.raulcg.ecommerce.exceptions;

public class QuantityNotAvailableException extends RuntimeException {

    public QuantityNotAvailableException(String message) {
        super(message);
    }
}
