package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    void deleteByCart_userId(UUID userId);
}
