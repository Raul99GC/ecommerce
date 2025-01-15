package com.raulcg.ecommerce.controllers;

import com.raulcg.ecommerce.request.SignupRequest;
import com.raulcg.ecommerce.responses.SignupResponse;
import com.raulcg.ecommerce.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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


}
