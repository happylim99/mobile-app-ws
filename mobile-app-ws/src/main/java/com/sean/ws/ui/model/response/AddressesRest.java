package com.sean.ws.ui.model.response;

import com.sean.ws.shared.dto.AddressDto;

public class AddressesRest {
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;

	public AddressesRest() {
		super();
	}

	public AddressesRest(AddressDto address) {
		super();
		this.addressId = address.getAddressId();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.streetName = address.getStreetName();
		this.postalCode = address.getPostalCode();
		this.type = address.getType();
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

}
