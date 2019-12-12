package com.sean.ws.shared;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// no longer work in junit5
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {
	
	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateUserId() {
		String userId = utils.generateUserId(30);
		String userId2 = utils.generateUserId(30);
		assertNotNull(userId);
		assertNotNull(userId2);
		assertTrue(userId.length() == 30);
		assertTrue(userId2.length() == 30);
		assertTrue(!userId.equalsIgnoreCase(userId2));
	}

	@Test
	//@Disabled
	void testHasTokenNotExpired() {
		String token = utils.generateEmailVerificationToken("ksjdbfksdbf");
		boolean hasTokenExpired = Utils.hasTokenExpired(token);
		assertNotNull(token);
		
		assertFalse(hasTokenExpired);
		
	}
	
	@Test
	final void testHasTokenExpired()
	{
		String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MUB0ZXN0LmNvbSIsImV4cCI6MTU3NTUzNzQ2Nn0._7YxSDCM0B6rpKLHmOUtwVk7mcffxNVgm-Co4mrYo6QRpxm-RqLOh-HZBTodwJ2LXzaEUODzxEFGPgewDQfWSg";
		boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);
		
		assertTrue(hasTokenExpired);
	}

}
