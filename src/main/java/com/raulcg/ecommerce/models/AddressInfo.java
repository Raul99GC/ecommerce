package com.raulcg.ecommerce.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {
    private UUID addressId;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String notes;
}