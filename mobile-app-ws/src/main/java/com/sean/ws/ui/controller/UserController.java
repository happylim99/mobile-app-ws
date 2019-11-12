package com.sean.ws.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users") //http:localhost:8080/users
public class UserController {
	
	@GetMapping()
	public String getUser()
	{
		return "list user";
	}
	
	@PostMapping
	public String CreateUser()
	{
		return "create use";
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
