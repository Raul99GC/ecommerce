package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
