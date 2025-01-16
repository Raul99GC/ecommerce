package com.raulcg.ecommerce.security;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.models.Role;
import com.raulcg.ecommerce.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

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
