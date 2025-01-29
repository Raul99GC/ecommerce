package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.request.CartRequest;
import com.raulcg.ecommerce.responses.CartResponse;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.cart.CartService;
import com.raulcg.ecommerce.utils.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shop/cart")
public class CartController {

    private final CartService cartService;
    private CartMapper cartMapper;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired(required = false)
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericApiResponse<Cart>> addProductToCart(@Validated @RequestBody CartRequest cartRequest) {
        Cart cart = cartService.addProductToCart(cartRequest);
        return ResponseEntity.ok(new GenericApiResponse<>(true, cart));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<CartResponse> getCartItems(@PathVariable UUID userId) {
        Cart cart = cartService.getCart(userId);

        CartResponse response = cartMapper.toCartResponse(cart);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<GenericApiResponse<CartResponse>> updateCartItem(@PathVariable UUID userId, @Validated @RequestBody CartRequest cartRequest) {
        Cart cart = cartService.updateCartItem(cartRequest);
        CartResponse response = cartMapper.toCartResponse(cart);
        return ResponseEntity.ok(new GenericApiResponse<>(true, response));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<GenericApiResponse<CartResponse>> deleteCartItem(@PathVariable UUID userId, @Validated @RequestBody CartRequest cartRequest) {
        Cart cart = cartService.deleteCartItem(cartRequest);
        CartResponse response = cartMapper.toCartResponse(cart);
        return ResponseEntity.ok(new GenericApiResponse<>(true, response));
    }

}
