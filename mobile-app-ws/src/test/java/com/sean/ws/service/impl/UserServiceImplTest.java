package com.sean.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sean.ws.exceptions.UserServiceException;
import com.sean.ws.io.entity.AddressEntity;
import com.sean.ws.io.entity.UserEntity;
import com.sean.ws.io.repository.UserRepository;
import com.sean.ws.shared.AmazonSES;
import com.sean.ws.shared.Utils;
import com.sean.ws.shared.dto.AddressDto;
import com.sean.ws.shared.dto.UserDto;

class UserServiceImplTest {
	String userId = "abcdefg";
	String encryptedPassword = "kasdfkdsjf";
	
	// for us to be able to call getUser() from UserServiceImpl
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	AmazonSES amazonSES;
	
	//@Mock
	//PasswordResetTokenRepository passwordResetTokenRepository;

	UserEntity userEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Sean");
		userEntity.setLastName("lim");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("test@test.com");
		userEntity.setEmailVerificationToken("akihsdksjss");
		userEntity.setAddresses(getAddressEntity());
	}

	@Test
	final void testGetUser()
	{

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@test.com");

		assertNotNull(userDto);
		assertEquals("Sean", userDto.getFirstName());
	}

	// junit 4
	// @Test(expected = UsernameNotFoundException.class
	@Test
	final void testGetUser_UsernameNotFoundException()
	{
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("test@test.com");
		});
	}
	
	@Test
	final void testCreateUser_CreateUserServiceException()
	{
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		assertThrows(UserServiceException.class, () -> {
			userService.createUser(getUserDto());
		});
	}
	
	@Test
	final void testCreateUser()
	{		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("sudjufsdf");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));
		
		UserDto storedUserDetails = userService.createUser(getUserDto());
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
		
		verify(utils, times(storedUserDetails.getAddresses().size())).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("qweqweqwe");
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
	
	private UserDto getUserDto()
	{
		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressDto());
		userDto.setFirstName("Sean");
		userDto.setLastName("Lim");
		userDto.setPassword("qweqweqwe");
		userDto.setEmail("test@test.com");
		
		return userDto;
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
	
	private List<AddressEntity> getAddressEntity()
	{
		List<AddressDto> addresses = getAddressDto();
		
		Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
		
		return new ModelMapper().map(addresses, listType);
	}

}
