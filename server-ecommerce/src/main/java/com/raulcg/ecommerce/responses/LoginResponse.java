package com.raulcg.ecommerce.responses;

import com.raulcg.ecommerce.models.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private boolean success;
    User user;
}
