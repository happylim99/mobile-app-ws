package com.sean.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.service.AddressService;
import com.sean.ws.service.UserService;
import com.sean.ws.shared.dto.AddressDto;
import com.sean.ws.shared.dto.UserDto;
import com.sean.ws.ui.model.request.UserDetailsRequestModel;
import com.sean.ws.ui.model.response.AddressesRest;
import com.sean.ws.ui.model.response.OperationStatusModel;
import com.sean.ws.ui.model.response.RequestOperationStatus;
import com.sean.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") //http:localhost:8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("/hello")
	public String hello()
	{
		return "hello";
	}
	
	@GetMapping(path="/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id)
	{		
		//UserRest returnValue = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		UserRest returnValue = modelMapper.map(userDto, UserRest.class);
		//BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest CreateUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		//UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("null pointer exception");
		//if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		//UserDto userDto = new UserDto();
		//Bean Utils.copyProperties doesn't do a good job when working with objects that contain other objects
		//BeanUtils.copyProperties(userDetails, userDto);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		//BeanUtils.copyProperties(createdUser, returnValue);
		UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public OperationStatusModel deleteUser(@PathVariable String id)
	{
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit)
	{
		List<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
	}
	
	@GetMapping(path="/all", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Page<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit)
	{
		Page<UserDto> users = userService.getAllUsers2(page, limit);
		
		Pageable pageable = PageRequest.of(page, limit);
		int totalElements = (int) users.getTotalElements();
		return new PageImpl<UserRest>(users.stream().map(user -> new UserRest(user))
				.collect(Collectors.toList()), pageable, totalElements);
	}
	
	@GetMapping(path="/{id}/addresses2", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<AddressesRest> getUserAddresses(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @PathVariable String id)
	{
		List<AddressesRest> returnValue = new ArrayList<>();
		
		List<AddressDto> addressDto = addressService.getAddresses(page, limit, id);
		
		if(addressDto != null && !addressDto.isEmpty())
		{
			java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressDto, listType); 
		}
		
		return returnValue;
	}
	
	@GetMapping(path="/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Page<AddressesRest> getUserAddresses2(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @PathVariable String id)
	{
		//Page<AddressesRest> returnValue = new ArrayList<>();
		
		Page<AddressDto> addresses = addressService.getAllAddresses(page, limit, id);
		
		if(addresses != null && !addresses.isEmpty())
		{
			//java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			//returnValue = new ModelMapper().map(addressDto, listType);
			Pageable pageable = PageRequest.of(page, limit);
			int totalElements = (int) addresses.getTotalElements();
			return new PageImpl<AddressesRest>(addresses.stream().map(address -> new AddressesRest(address))
					.collect(Collectors.toList()), pageable, totalElements);
		}
		
		return null;
	}
	
	@GetMapping(path="/{id}/addresses/{address}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public AddressesRest getUserAddress(@PathVariable String id, @PathVariable String address)
	{		
		//UserRest returnValue = new UserRest();
		
		AddressDto addressDto = addressService.getAddressByAddressId(address);
		ModelMapper modelMapper = new ModelMapper();
		AddressesRest returnValue = modelMapper.map(addressDto, AddressesRest.class);
		//BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}

}
