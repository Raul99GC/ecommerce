package com.raulcg.ecommerce.request;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CartRequest {

    private UUID userId;

    private UUID productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    public CartRequest(UUID userId, UUID productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }


}
