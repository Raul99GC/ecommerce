package com.raulcg.ecommerce.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupResponse {

    private String message;
    private boolean success;

    public SignupResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
