package com.raulcg.ecommerce.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductReviewDTO {
    private String reviewMessage;
    private int reviewValue;
    private String userName;
    private UUID productId;
    private UUID userId;
}