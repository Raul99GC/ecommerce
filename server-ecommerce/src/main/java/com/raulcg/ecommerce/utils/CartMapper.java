package com.raulcg.ecommerce.utils;

import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.models.CartItem;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public Map<String, Object> toCartResponse(Cart cart) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("userId", cart.getUser().getId());
        response.put("items", cart.getItems().stream()
                .filter(item -> item.getProduct() != null)
                .map(this::toCartItemMap)
                .collect(Collectors.toSet()));
        return response;
    }

    private Map<String, Object> toCartItemMap(CartItem item) {
        Map<String, Object> response = new HashMap<>();
        response.put("productId", item.getProduct().getId());
        response.put("image", item.getProduct().getImage());
        response.put("title", item.getProduct().getTitle());
        response.put("price", item.getProduct().getPrice());
        response.put("salePrice", item.getProduct().getPrice()); // Cambiar si es necesario
        response.put("quantity", item.getQuantity());
        return response;
    }
}