package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.models.Address;
import com.raulcg.ecommerce.request.AddressRequest;
import com.raulcg.ecommerce.responses.AddressResponse;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.address.IAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shop/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;
    private final ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<GenericApiResponse<AddressResponse>> addAddress(
            @Valid @RequestBody AddressRequest request) {
//        TODO: refactorizar los parametros de la petición createAddress
        Address createdAddress = addressService.createAddress(request.getUserId(), request);

        // ? aqui se ocupa la dependencia modelMapper para convertir la entidad Address a un objeto AddressResponse
        // ? lo ocupo nada mas para probar la integración de modelMapper
        AddressResponse response = modelMapper.map(createdAddress, AddressResponse.class);

        return ResponseEntity.ok(new GenericApiResponse<>(true, response));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<GenericApiResponse<List<AddressResponse>>> getAddresses(
            @PathVariable UUID userId) {

        List<Address> addresses = addressService.getUserAddresses(userId);
        List<AddressResponse> response = addresses.stream()
                .map(address -> modelMapper.map(address, AddressResponse.class))
                .toList();

        return ResponseEntity.ok(new GenericApiResponse<>(true, response));
    }

    @DeleteMapping("/delete/{userId}/{addressId}")
    public ResponseEntity<GenericApiResponse<Void>> deleteAddress(
            @PathVariable UUID userId,
            @PathVariable UUID addressId) {

        addressService.removeAddress(userId, addressId);
        return ResponseEntity.ok(new GenericApiResponse<>(true, null));
    }

    @PutMapping("/update/{userId}/{addressId}")
    public ResponseEntity<GenericApiResponse<AddressResponse>> updateAddress(
            @PathVariable UUID userId,
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequest request) {

        Address updatedAddress = addressService.updateUserAddress(userId, addressId, request);
        AddressResponse response = modelMapper.map(updatedAddress, AddressResponse.class);

        return ResponseEntity.ok(new GenericApiResponse<>(true, response));
    }
}