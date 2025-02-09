package com.raulcg.ecommerce.controllers.auth;

import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.request.LoginRequest;
import com.raulcg.ecommerce.request.SignupRequest;
import com.raulcg.ecommerce.responses.CheckAuthResponse;
import com.raulcg.ecommerce.responses.LoginResponse;
import com.raulcg.ecommerce.responses.SignupResponse;
import com.raulcg.ecommerce.security.jwt.JwtUtils;
import com.raulcg.ecommerce.security.services.UserDetailsImpl;
import com.raulcg.ecommerce.services.user.UserService;
import com.raulcg.ecommerce.utils.AuthUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/register")
    public ResponseEntity<SignupResponse> registerUser(@RequestBody SignupRequest signupRequest) {

        try {
            userService.registerUser(signupRequest);
            SignupResponse response = new SignupResponse("User registered successfully", true);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            SignupResponse response = new SignupResponse(e.getMessage(), false);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Validated @RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "success", false,
                    "message", "Invalid credentials. Please check your username and password."
            ));
        }

        Authentication authentication;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateTokenFromUserDetails(userDetails);

        Cookie tokenCookie = new Cookie("token", jwt);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(60 * 60 * 2); // Expira en 2 hora
        response.addCookie(tokenCookie);

        LoginResponse responseBody = new LoginResponse();
        responseBody.setUser(userOptional.get());
        responseBody.setMessage("Logged in successfully!");
        responseBody.setSuccess(true);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        // Crear una cookie con el mismo nombre ("token") y valor vacío
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Devolver respuesta
        return ResponseEntity.ok().body(
                new Response(true, "Logged out successfully!")
        );
    }

    public record Response(boolean success, String message) {
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth() {
        Optional<User> user = authUtils.loggedInUser();
        CheckAuthResponse response = new CheckAuthResponse();

        if (user.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("You are not logged in.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.setSuccess(true);
        response.setMessage("You are logged in.");
        response.setUser(user.get());
        return ResponseEntity.ok(response);
    }

}
