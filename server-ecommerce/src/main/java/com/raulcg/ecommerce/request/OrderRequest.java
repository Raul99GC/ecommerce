package com.raulcg.ecommerce.request;

import com.raulcg.ecommerce.dtos.AddressInfoDTO;
import com.raulcg.ecommerce.enums.OrderStatus;
import com.raulcg.ecommerce.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private UUID userId;
    @NotNull
    private UUID cartId;
//    @NotNull
//    private List<OrderItemDTO> cartItems;
    @NotNull
    private AddressInfoDTO addressInfo;
    @NotNull
    private OrderStatus orderStatus;
    @NotBlank
    private String paymentMethod;
    @NotNull
    private PaymentStatus paymentStatus;
    @NotNull
    private Long totalAmount;

    private String token;
    private String payerId;

    private LocalDateTime orderDate;
    private LocalDateTime orderUpdateDate;
}
