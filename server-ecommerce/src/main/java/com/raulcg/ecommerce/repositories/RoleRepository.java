package com.raulcg.ecommerce.repositories;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<com.raulcg.ecommerce.models.Role, Long> {

    Optional<Role> findByRole(UserRole roleName);
}
