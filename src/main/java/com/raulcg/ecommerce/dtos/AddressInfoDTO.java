package com.raulcg.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDTO {
    private UUID addressId;
    private String address;
    private String city;
    private String pincode;
    private String phone;
    private String notes;
}
