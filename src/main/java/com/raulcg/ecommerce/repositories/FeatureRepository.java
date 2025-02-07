package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeatureRepository extends JpaRepository<Feature, UUID> {
}
