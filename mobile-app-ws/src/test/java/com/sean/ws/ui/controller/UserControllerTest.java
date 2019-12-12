package com.sean.ws.ui.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sean.ws.service.UserService;
import com.sean.ws.shared.dto.AddressDto;
import com.sean.ws.shared.dto.UserDto;
import com.sean.ws.ui.model.response.UserRest;

class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	
	UserDto userDto;
	
	final String USER_ID = "ksuhdkasd";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userDto = new UserDto();
        userDto.setFirstName("Sergey");
        userDto.setLastName("Kargopolov");
        userDto.setEmail("test@test.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setUserId(USER_ID);
        userDto.setAddresses(getAddressDto());
        userDto.setEncryptedPassword("xcf58tugh47");
	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		
		UserRest userRest = userController.getUser(USER_ID);
		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertEquals(userDto.getLastName(), userRest.getLastName());
		assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
		
	}
	
	private List<AddressDto> getAddressDto()
	{
		AddressDto addressDto = new AddressDto();
		addressDto.setType("shipping");
		addressDto.setCity("city1");
		addressDto.setCountry("country1");
		addressDto.setStreetName("streetname1");
		addressDto.setPostalCode("postalCode1");
		
		AddressDto addressDto2 = new AddressDto();
		addressDto.setType("billing");
		addressDto.setCity("city2");
		addressDto.setCountry("country2");
		addressDto.setStreetName("streetname2");
		addressDto.setPostalCode("postalCode2");
		
		List<AddressDto> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(addressDto2);
		
		return addresses;
	}

}
