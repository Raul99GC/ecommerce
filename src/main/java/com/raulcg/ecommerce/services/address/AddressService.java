package com.raulcg.ecommerce.services.address;

import com.raulcg.ecommerce.models.Address;
import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.AddressRepository;
import com.raulcg.ecommerce.repositories.UserRepository;
import com.raulcg.ecommerce.request.AddressRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Address createAddress(UUID userId, AddressRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        
        Address newAddress = modelMapper.map(request, Address.class);
        newAddress.setId(UUID.randomUUID());
        newAddress.setUser(user);
        
        return addressRepository.save(newAddress);
    }

    @Override
    public List<Address> getUserAddresses(UUID userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void removeAddress(UUID userId, UUID addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        
        addressRepository.delete(address);
    }

    @Override
    public Address updateUserAddress(UUID userId, UUID addressId, AddressRequest request) {
        Address existingAddress = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Dirección no encontrada"));
        
        modelMapper.map(request, existingAddress);
        return addressRepository.save(existingAddress);
    }
}