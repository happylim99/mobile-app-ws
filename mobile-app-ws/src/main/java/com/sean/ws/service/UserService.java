package com.sean.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sean.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto user);

}
