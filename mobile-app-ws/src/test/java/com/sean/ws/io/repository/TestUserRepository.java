package com.sean.ws.io.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sean.ws.io.entity.UserEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestUserRepository {
	
	@Autowired
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 2);
		Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(page);
		
        List<UserEntity> userEntities = page.getContent();
        //page.map(x -> x.toString()).forEach(System.out::println);
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);
	}
	
	@Test
	final void testFindUserByFirstAndLastName()
	{
		String firstName="test1";
		String lastName="test1";
		List<UserEntity> users = userRepository.findUserByFirstAndLastName(firstName, lastName);
		
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().equals(firstName));
	}
	
	@Test
	final void testFindUserByFirstName()
	{
		String firstName="test1";
		List<UserEntity> users = userRepository.findUserByFirstName(firstName);
		
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().equals(firstName));
	}
	
	@Test
	final void testFindUserByLastName()
	{
		String lastName="test1";
		List<UserEntity> users = userRepository.findUserByFirstName(lastName);
		
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().equals(lastName));
	}
	
	@Test
	final void testFindUserByKeyword()
	{
		String keyword="est";
		List<UserEntity> users = userRepository.findUserByKeyword(keyword);
		
		assertNotNull(users);
		assertTrue(users.size() == 4);
		
		UserEntity user = users.get(0);
		assertTrue(
				user.getFirstName().contains(keyword) || 
				user.getLastName().contains(keyword)
				);
	}
	
	@Test
	final void testFindUserFirstNameAndLastNameByKeyword()
	{
		String keyword="est";
		List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
		
		assertNotNull(users);
		assertTrue(users.size() == 4);
		
		Object[] user = users.get(0);
		
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		
		assertTrue(user.length == 2);
		assertNotNull(userFirstName);
		assertNotNull(userLastName);
		
	}
	
	@Test
	final void testUpdateUserEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = false;
		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "XtIO69F77xUR0YePoLfFsQOvW3bIkv");
		
		UserEntity storedDetails = userRepository.findByUserId("XtIO69F77xUR0YePoLfFsQOvW3bIkv");
		
		assertTrue(storedDetails.getEmailVerificationStatus() == newEmailVerificationStatus);
		
	}
	
	@Test
	final void testFindUserEntityByUserId()
	{
		String userId = "XtIO69F77xUR0YePoLfFsQOvW3bIkv";
		UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
		
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
				
	}
	
	@Test
	final void testGetUserEntityFullNameById()
	{
		String userId = "XtIO69F77xUR0YePoLfFsQOvW3bIkv";
		List<Object[]> userEntity = userRepository.getUserEntityFullNameById(userId);
		
		assertNotNull(userEntity);
		assertTrue(userEntity.size() == 1);
				
	}
	
	@Test
	final void testUpdateUserEntityEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = false;
		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "XtIO69F77xUR0YePoLfFsQOvW3bIkv");
		
		UserEntity storedDetails = userRepository.findByUserId("XtIO69F77xUR0YePoLfFsQOvW3bIkv");
		
		assertTrue(storedDetails.getEmailVerificationStatus() == newEmailVerificationStatus);
		
	}

}
