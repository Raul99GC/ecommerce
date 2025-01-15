package com.raulcg.ecommerce.services.user;

import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.request.SignupRequest;

import java.util.Optional;

public interface IUserService {

    User registerUser(SignupRequest signupRequest);

    User createUser(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
