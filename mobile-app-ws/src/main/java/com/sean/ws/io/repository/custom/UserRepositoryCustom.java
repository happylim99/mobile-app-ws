package com.sean.ws.io.repository.custom;

import java.util.List;

import com.sean.ws.io.entity.UserEntity;

public interface UserRepositoryCustom {
	
	List<UserEntity> findUserByFirstNameAndLastNameCustom(String firstName, String lastName);

}
