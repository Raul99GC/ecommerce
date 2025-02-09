package com.raulcg.ecommerce.services.cart;

import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.request.CartRequest;

import java.util.UUID;

public interface ICartService {

    // add product to cart
    Cart addProductToCart(CartRequest cartRequest);

    Cart getCart(UUID userId);

    Cart updateCartItem(CartRequest cartRequest);

    Cart deleteCartItem(UUID userId, UUID productId);

}
