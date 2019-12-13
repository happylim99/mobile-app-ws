package com.sean.ws.restassured.shared;

import io.restassured.RestAssured;

public class TestUtils {
	
	public static final String CONTEXT_PATH="/mobile-app-ws";
	public static final String JSON="application/json";
	
	public static void setup()
	{
		RestAssured.baseURI="http://localhost";
		RestAssured.port=8080;
	}
}
