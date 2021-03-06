package com.sean.ws.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//not work with JPQL
//@Entity(name="users")
@Entity
@Table(name="users")
public class UserEntity implements Serializable{

	private static final long serialVersionUID = -8732757708190313677L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	
	@Column(nullable=false, length=50)
	private String lastName;
	
	@Column(nullable=false, length=120, unique=true)
	//@Column(nullable=false, length=120)
	private String email;
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	//@Column(nullable=false)
	private String emailVerificationToken;
	
	// the default method below not working on all platform
	//@Column(nullable=false, columnDefinition = "boolean default false")
	@Column(nullable=false)
	private Boolean emailVerificationStatus = false;
	
	@JsonIgnoreProperties("user")
	@OneToMany(mappedBy="userDetails", cascade=CascadeType.ALL)
	private List<AddressEntity> addresses;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return String.format("FirstName : " + firstName + "\nEmail : " + email);
		//return String.format("UserEntity [firstName=%s, email=%s]", firstName, email);
	}

}
