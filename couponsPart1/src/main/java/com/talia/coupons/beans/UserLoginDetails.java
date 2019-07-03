package com.talia.coupons.beans;

import com.talia.coupons.enums.ClientType;

public class UserLoginDetails {

	// ATTRIBUTES 
	
	private String userEmail;
	private String password;
	private ClientType type;
	private int token;
	
	
	// GETTERS AND SETTERS
	
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}


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
	// everything but token
	public UserLoginDetails(String userEmail, String password, ClientType type, long companyID) {
		super();
		this.userEmail = userEmail;
		this.password = password;
		this.type = type;
//		this.companyID = companyID;
	}
	// with everything
	public UserLoginDetails(String userEmail, String password, ClientType type, int token, long companyID) {
		super();
		this.userEmail = userEmail;
		this.password = password;
		this.type = type;
		this.token = token;
//		this.companyID = companyID;
	}
	// empty constructor
	public UserLoginDetails() {
		super();
	}
	
	// FUNCTION
	
	public String toString() {
		return "userEmail:" + userEmail + ", password:" + password + ", type:" + type;
	}
	
}
