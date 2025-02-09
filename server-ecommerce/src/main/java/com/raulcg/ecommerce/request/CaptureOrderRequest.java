package com.raulcg.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptureOrderRequest {
    private UUID orderId;
    private String token;
    private String payerId;

}