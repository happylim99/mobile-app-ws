package com.sean.ws.shared.dto;

import com.sean.ws.io.entity.AddressEntity;

public class AddressDto {
	private long id;
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;
	private UserDto userDetails;

	public AddressDto() {
		super();
	}

	public AddressDto(AddressEntity address) {
		super();
		this.addressId = address.getAddressId();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.streetName = address.getStreetName();
		this.postalCode = address.getPostalCode();
		this.type = address.getType();
		//this.userDetails = address.getUserDetails();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDto getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}

}
