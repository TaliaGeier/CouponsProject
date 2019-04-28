package com.talia.coupons.beans;

import com.talia.coupons.enums.ClientType;

public class UserLoginDetails {

	private String userEmail;
	private String password;
	private ClientType type;
	
	// GETTERS AND SETTERS
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}
	
	// CONSTRUCTOR
	
	public UserLoginDetails(String userEmail, String password, ClientType type) {
		super();
		this.userEmail = userEmail;
		this.password = password;
		this.type = type;
	}
	// empty constructor
	public UserLoginDetails() {
		super();
	}
	
	public String toString() {
		return "userEmail:" + userEmail + ", password:" + password + ", type:" + type;
	}
	
}
