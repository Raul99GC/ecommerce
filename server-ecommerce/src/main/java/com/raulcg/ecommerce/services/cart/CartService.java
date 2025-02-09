package com.raulcg.ecommerce.services.cart;

import com.raulcg.ecommerce.exceptions.CartNotFoundException;
import com.raulcg.ecommerce.exceptions.ProductNotFoundException;
import com.raulcg.ecommerce.exceptions.QuantityNotAvailableException;
import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.models.CartItem;
import com.raulcg.ecommerce.repositories.CartItemRepository;
import com.raulcg.ecommerce.repositories.CartRepository;
import com.raulcg.ecommerce.repositories.ProductRepository;
import com.raulcg.ecommerce.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public Cart addProductToCart(CartRequest cartRequest) {

        Cart cart = cartRepository.findByUserId(cartRequest.getUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));

        // Verificar si el producto ya está en el carrito
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartRequest.getProductId()))
                .findFirst();

        if (cartItemOptional.isPresent()) {

            int currentQuantity = cartItemOptional.get().getQuantity();
            int newQuantity = currentQuantity + cartRequest.getQuantity();

            if (newQuantity > cartItemOptional.get().getProduct().getTotalStock()) {
                throw new QuantityNotAvailableException("Quantity not available!");
            }

            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
            return cart;
        }

        // Si el producto no está en el carrito, agregarlo

        Product product = productRepository.findById(cartRequest.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Product not found!"));

        CartItem cartItem = cartItemRepository.save(new CartItem(cart, product, cartRequest.getQuantity()));

        cart.getItems().add(cartItem);
        return cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public Cart getCart(UUID userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));
    }


    @Transactional
    @Override
    public Cart updateCartItem(CartRequest cartRequest) {

        Cart cart = cartRepository.findByUserId(cartRequest.getUserId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));

        // Verificar si el producto ya está en el carrito
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartRequest.getProductId()))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            // Si el producto ya está en el carrito, actualizar la cantidad
            int newQuantity = cartRequest.getQuantity();
            if (newQuantity > cartItemOptional.get().getProduct().getTotalStock()) {
                throw new QuantityNotAvailableException("Quantity not available!");
            }

            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartRequest.getQuantity());
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
            return cart;
        }


        return cart;
    }

    @Transactional
    @Override
    public Cart deleteCartItem(UUID userId, UUID productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));
        // Verificar si el producto ya está en el carrito
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.getItems().remove(cartItem);
            return cartRepository.save(cart);
        }

        throw new ProductNotFoundException("Product not found!");
    }

}
