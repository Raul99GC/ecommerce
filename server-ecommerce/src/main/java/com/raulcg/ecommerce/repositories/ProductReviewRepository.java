package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {
    List<ProductReview> findByProductId(UUID productId);
}