package com.raulcg.ecommerce.configs;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.models.Role;
import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.RoleRepository;
import com.raulcg.ecommerce.services.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        if (roleRepository.findByRole(UserRole.ADMIN).isEmpty()) {
            roleRepository.save(new Role(UserRole.ADMIN));
        }
        if (roleRepository.findByRole(UserRole.USER).isEmpty()) {
            roleRepository.save(new Role(UserRole.USER));
        }

        if (!userService.existUser("admin@gmail.com")) {
            User user = new User("admin", "admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(
                    roleRepository.findByRole(UserRole.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Role not found"))
            );
            userService.createUser(user);
        }
    }
}