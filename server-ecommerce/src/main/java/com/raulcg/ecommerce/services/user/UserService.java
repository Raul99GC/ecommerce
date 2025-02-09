package com.raulcg.ecommerce.services.user;

import com.raulcg.ecommerce.enums.UserRole;
import com.raulcg.ecommerce.exceptions.EmailAlreadyExistsException;
import com.raulcg.ecommerce.exceptions.UsernameAlreadyExistsException;
import com.raulcg.ecommerce.models.Cart;
import com.raulcg.ecommerce.models.Role;
import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.CartRepository;
import com.raulcg.ecommerce.repositories.RoleRepository;
import com.raulcg.ecommerce.repositories.UserRepository;
import com.raulcg.ecommerce.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private CartRepository cartRepository;

    // Constructor con dependencias requeridas
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Setter para la dependencia opcional
    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    @Override
    public User registerUser(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already taken");
        } else if (userRepository.existsByUserName(signupRequest.getUserName())) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }
        Role role = roleRepository.findByRole(UserRole.USER).orElseThrow(() -> new RuntimeException("Role not found"));
        Cart cartSaved = new Cart();
        if (cartRepository != null) {
            cartRepository.save(new Cart());
        }
        User newUser = new User(signupRequest.getUserName(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setRole(role);
        newUser.setCart(cartSaved);

        User userSaved = userRepository.save(newUser);


        return userSaved;
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Boolean existUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
