package com.sean.ws.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 4541416339246235731L;

	public UserServiceException(String message) {
		super(message);
	}

}
