package com.raulcg.ecommerce.utils;

import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUtils {


    @Autowired
    private UserRepository userRepository;

    public Optional<User> loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUserName(username);
    }

}
