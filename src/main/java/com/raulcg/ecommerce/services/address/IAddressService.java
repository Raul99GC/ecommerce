package com.raulcg.ecommerce.services.address;

import com.raulcg.ecommerce.models.Address;
import com.raulcg.ecommerce.request.AddressRequest;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    Address createAddress(UUID userId, AddressRequest request);
    List<Address> getUserAddresses(UUID userId);
    void removeAddress(UUID userId, UUID addressId);
    Address updateUserAddress(UUID userId, UUID addressId, AddressRequest request);
}