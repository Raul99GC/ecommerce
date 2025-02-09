package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);
    Optional<Address> findByIdAndUserId(UUID addressId, UUID userId);
}