package com.raulcg.ecommerce.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    @Size(max = 50, message = "Email must be between 5 and 50 characters long")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 40, message = "Password must be between 8 and 40 characters long")
    private String password;
}
