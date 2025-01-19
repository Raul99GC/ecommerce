package com.raulcg.ecommerce.responses;

import com.raulcg.ecommerce.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckAuthResponse {

    private String message;
    private boolean success;
    private User user;
}
