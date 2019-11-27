package com.sean.ws.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sean.ws.shared.dto.AddressDto;

public interface AddressService {
	List<AddressDto> getAddresses(int page, int limit, String userId);
	Page<AddressDto> getAllAddresses(int page, int limit, String userId);
	AddressDto getAddressByAddressId(String addressId);
}
