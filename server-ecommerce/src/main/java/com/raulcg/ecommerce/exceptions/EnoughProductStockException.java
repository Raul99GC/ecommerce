package com.raulcg.ecommerce.exceptions;

public class EnoughProductStockException  extends RuntimeException {
    public EnoughProductStockException(String message) {
        super(message);
    }
}
