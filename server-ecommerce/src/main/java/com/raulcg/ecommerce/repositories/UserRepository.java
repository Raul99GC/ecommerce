package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByUserName(String username);

    Optional<User> findByUserName(String username);
}
