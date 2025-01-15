package com.raulcg.ecommerce.security;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.models.Role;
import com.raulcg.ecommerce.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository) {

        return args -> {
            if (roleRepository.findByRole(UserRole.ADMIN).isEmpty()) {
                roleRepository.save(new Role(UserRole.ADMIN));
            }
            if (roleRepository.findByRole(UserRole.USER).isEmpty()) {
                roleRepository.save(new Role(UserRole.USER));
            }
        };
    }
}
