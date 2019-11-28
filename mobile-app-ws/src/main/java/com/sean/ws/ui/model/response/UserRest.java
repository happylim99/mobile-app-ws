package com.sean.ws.ui.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.ResourceSupport;

import com.sean.ws.shared.dto.UserDto;

public class UserRest extends ResourceSupport{

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<AddressesRest> addresses;

	public UserRest() {
		super();
	}

	public UserRest(UserDto user) {
		super();
		//ModelMapper modelMapper = new ModelMapper();
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.addresses = user.getAddresses().stream().map(AddressesRest::new).collect(Collectors.toCollection(ArrayList::new));
		
		//this.addresses = modelMapper.map(user.getAddresses(), new TypeToken<List<AddressesRest>>(){}.getType());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AddressesRest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressesRest> addresses) {
		this.addresses = addresses;
	}

}
