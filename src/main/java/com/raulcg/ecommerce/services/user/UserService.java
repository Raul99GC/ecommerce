package com.raulcg.ecommerce.services.user;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.exceptions.EmailAlreadyExistsException;
import com.raulcg.ecommerce.exceptions.UsernameAlreadyExistsException;
import com.raulcg.ecommerce.models.Role;
import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.RoleRepository;
import com.raulcg.ecommerce.repositories.UserRepository;
import com.raulcg.ecommerce.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registerUser(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already taken");
        } else if (userRepository.existsByUserName(signupRequest.getUserName())) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }
        Role role = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new RuntimeException("Role not found"));

        User newUser = new User(signupRequest.getUserName(), signupRequest.getEmail(), signupRequest.getPassword());
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
