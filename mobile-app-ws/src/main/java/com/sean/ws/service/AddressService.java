package com.sean.ws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sean.ws.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddresses(int page, int limit, String userId);
	Page<AddressDto> getAllAddresses(String userId, Pageable pageable);
	AddressDto getAddressByAddressId(String addressId);
}
