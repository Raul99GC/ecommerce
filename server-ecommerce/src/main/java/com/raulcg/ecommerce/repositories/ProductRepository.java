package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p " +
            "WHERE (:categories IS NULL OR p.category IN :categories) " +
            "AND (:brands IS NULL OR p.brand IN :brands) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'price-lowtohigh' THEN p.price END ASC, " +
            "CASE WHEN :sortBy = 'price-hightolow' THEN p.price END DESC, " +
            "CASE WHEN :sortBy = 'title-atoz' THEN p.title END ASC, " +
            "CASE WHEN :sortBy = 'title-ztoa' THEN p.title END DESC")
    List<Product> findFilteredProducts(
            @Param("categories") List<String> categories,
            @Param("brands") List<String> brands,
            @Param("sortBy") String sortBy
    );

    // search by category
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT COALESCE(AVG(r.reviewValue), 0) FROM ProductReview r WHERE r.product.id = :productId")
    Integer findAverageRatingByProductId(@Param("productId") UUID productId);
}
