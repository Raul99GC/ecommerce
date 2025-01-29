package com.raulcg.ecommerce.utils;

import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.repositories.CartItem;
import com.raulcg.ecommerce.responses.CartItemResponse;
import com.raulcg.ecommerce.responses.CartResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setItems(cart.getItems().stream()
                .filter(item -> item.getProduct() != null)
                .map(this::toCartItemResponse)
                .collect(Collectors.toSet()));
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());
        return response;
    }

    private CartItemResponse toCartItemResponse(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setProductId(item.getProduct().getId());
        response.setImage(item.getProduct().getImage());
        response.setTitle(item.getProduct().getTitle());
        response.setPrice(item.getProduct().getPrice());
        response.setSalePrice(item.getProduct().getPrice()); // Cambiar si es necesario
        response.setQuantity(item.getQuantity());
        return response;
    }
}