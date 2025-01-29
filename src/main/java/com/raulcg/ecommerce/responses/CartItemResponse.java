package com.raulcg.ecommerce.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CartItemResponse {

    private UUID productId;
    private String image;
    private String title;
    private double price;
    private double salePrice;
    private int quantity;
}
