package com.sean.ws.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.service.UserService;
import com.sean.ws.ui.model.request.UserDetailsRequestModel;
import com.sean.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") //http:localhost:8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping()
	public String getUser()
	{
		return "list user";
	}
	
	@PostMapping
	public UserRest CreateUser(@RequestBody UserDetailsRequestModel userDetails)
	{
		return null;
	}
	
	@PutMapping
	public String updateUser()
	{
		return "update user";
	}
	
	@DeleteMapping
	public String deleteUser()
	{
		return "delete user";
	}

}
