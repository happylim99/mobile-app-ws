package com.sean.ws.restassured;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.sean.ws.restassured.shared.TestUtils;

import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUsersWebServiceEndpoint {
	
	private final String EMAIL_ADDRESS = "happylim99@gmail.com";
	private final String PASSWORD = "123";
	private final String json = TestUtils.JSON;
	private static String authorization;
	private static String userId;
	private static List<Map<String, String>> addresses;

	@BeforeEach
	void setUp() throws Exception {
		TestUtils.setup();
	}

	@Test
	@Order(1)
	final void testUserLogin() {
		Map<String, String> loginDetails = new HashMap<>();
		loginDetails.put("email", EMAIL_ADDRESS);
		loginDetails.put("password", PASSWORD);
		
		Response response = given()
		.accept(json)
		.contentType(json)
		.body(loginDetails)
		.when()
		.post(TestUtils.CONTEXT_PATH + "/users/login")
		.then()
		.statusCode(200)
		.extract()
		.response();
		
		authorization = response.header("Authorization");
		userId = response.header("UserID");
		
		assertNotNull(authorization);
		assertNotNull(userId);
		
	}
	
	@Test
	@Order(2)
	final void testGetUserDetails() {
		
		Response response = given()
				.pathParam("id", userId)
				.header("Authorization", authorization)
				.accept(json)
				.contentType(json)
				.when()
				//.get(TestUtils.CONTEXT_PATH + "/users/" + userId)
				.get(TestUtils.CONTEXT_PATH + "/users/{id}")
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		String userPublicId = response.jsonPath().getString("userId");
		String userEmail = response.jsonPath().getString("email");
		String firstName = response.jsonPath().getString("firstName");
		String lastName = response.jsonPath().getString("lastName");
		addresses = response.jsonPath().getList("addresses");
		String addressId = addresses.get(0).get("addressId");
		
		assertNotNull(userPublicId);
		assertNotNull(userEmail);
		assertNotNull(firstName);
		assertNotNull(lastName);
		assertEquals(EMAIL_ADDRESS, userEmail);

		assertTrue(addresses.size() == 1);
		assertTrue(addressId.length() == 30);
		
	}
	
	@Test
	@Order(3)
	final void testUpdateUser()
	{
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("firstName", "qwe");
		userDetails.put("lastName", "rty");
		
		Response response = given()
				.header("Authorization", authorization)
				.accept(json)
				.contentType(json)
				.pathParam("id", userId)
				.body(userDetails)
				.when()
				.put(TestUtils.CONTEXT_PATH + "/users/{id}")
				.then()
				.statusCode(200)
				.extract()
				.response();
		
		String firstName = response.jsonPath().getString("firstName");
		String lastName = response.jsonPath().getString("lastName");
		
		List<Map<String, String>> storedAddresses = response.jsonPath().getList("addresses");

		assertEquals("qwe", firstName);
        assertEquals("rty", lastName);
        assertNotNull(storedAddresses);
        assertTrue(addresses.size() == storedAddresses.size());
        assertEquals(addresses.get(0).get("streetName"), storedAddresses.get(0).get("streetName"));
		
	}
	
	@Test
	@Order(4)
	@Disabled
	final void testDeleteUser()
	{
		Response response = given()
		.header("Authorization",authorization)
		.accept(json)
		.pathParam("id", userId)
		.when()
		.delete(TestUtils.CONTEXT_PATH + "/users/{id}")
		.then()
		.statusCode(200)
		.contentType(json)
		.extract()
		.response();
		
		String operationResult = response.jsonPath().getString("operationResult");
		assertEquals("SUCCESS", operationResult);
		
	}

}
