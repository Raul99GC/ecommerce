package com.raulcg.ecommerce.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderResponse {
    boolean success;
    String approvalURL;
    UUID orderId;
}
