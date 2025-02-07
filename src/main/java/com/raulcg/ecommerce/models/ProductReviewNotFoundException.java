package com.raulcg.ecommerce.models;

public class ProductReviewNotFoundException extends RuntimeException {
    public ProductReviewNotFoundException(String message) {
        super(message);
    }
}
