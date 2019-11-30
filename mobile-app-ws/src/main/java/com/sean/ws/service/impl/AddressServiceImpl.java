package com.sean.ws.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sean.ws.exceptions.NotFoundException;
import com.sean.ws.io.entity.AddressEntity;
import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.repository.AddressRepository;
import com.sean.ws.io.repository.UserRepository;
import com.sean.ws.service.AddressService;
import com.sean.ws.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddresses(int page, int limit, String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null)
			return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		for(AddressEntity addressEntity:addresses)
		{
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}
		
		return returnValue;
	}
	
	@Override
	//public Page<AddressDto> getAllAddresses(int page, int limit, String userId) {
	public Page<AddressDto> getAllAddresses(String userId, Pageable pageable) {
		//if(page>0) page = page-1;
		
		//Pageable pageable = PageRequest.of(page, limit, Sort.by("city").ascending().and(Sort.by("country").ascending()));
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		//if(userEntity == null)
			//return returnValue;
		
		Page<AddressEntity> addresses = addressRepository.findAllByUserDetails(pageable, userEntity);
		
		int totalElements = (int) addresses.getTotalElements();
		
		return new PageImpl<AddressDto>(addresses.stream().map(address -> new AddressDto(address))
				.collect(Collectors.toList()), pageable, totalElements);
		/*
		Page<AddressDto> returnValue = (Page<AddressDto>) modelMapper.map(addresses, AddressDto.class);
		
		return returnValue;
if(page>0) page = page-1;
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by("firstName").descending().and(Sort.by("lastName").ascending()));
		
		Page<UserEntity> users = userRepository.findAll(pageable);
		
		int totalElements = (int) users.getTotalElements();
		return new PageImpl<UserDto>(users.stream().map(user -> new UserDto(user))
				.collect(Collectors.toList()), pageable, totalElements);
				*/
	}

	@Override
	public AddressDto getAddressByAddressId(String addressId) {
		AddressDto returnValue = null;
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if (addressEntity == null) throw new NotFoundException("Address with ID " + addressId + " not found");
			//return null;
		
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(addressEntity, AddressDto.class);
		//BeanUtils.copyProperties(uEntity, returnValue);
		
		return returnValue;
	}
}
