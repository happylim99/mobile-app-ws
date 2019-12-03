package com.sean.ws.security;

import com.sean.ws.SpringApplicationContext;

public class SecurityConstants {
	public static final long EXPIRATION_TIME = 864000000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String EMAIL_VERIFICATION = "/users/email-verification";
//	public static final String TOKEN_SECRET = "abcd1234";
	
	public static String getTokenSecret()
	{
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

}
