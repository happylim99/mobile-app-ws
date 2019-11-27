package com.sean.ws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sean.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto user);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);
	//Page<UserEntity> getAllUsers(int page, int limit);
	Page<UserDto> getAllUsers2(int page, int limit);
	
	/*
	@Query(value = "SELECT * FROM Person p WHERE p.firstName = :firstName",
	        countQuery = "SELECT count(*) Person p WHERE p.firstName = :firstName",
	        nativeQuery = true)
	Page<Person> findByFirstNameNativeSQL(@Param("firstName") String firstName, Pageable pageable);
	*/
}
