package com.raulcg.ecommerce.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private UUID id;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String notes;
}