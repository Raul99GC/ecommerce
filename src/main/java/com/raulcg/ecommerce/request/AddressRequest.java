package com.raulcg.ecommerce.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AddressRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String pincode;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phone;

    private String notes;
}
