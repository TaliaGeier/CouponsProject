package com.talia.coupons.beans;


public class User {
	
	//ATTRIBUTES
	private long userId;
	private Long companyId;
	private UserLoginDetails userLoginDetails;
	
	// GETTERS AND SETTERS
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public UserLoginDetails getUserLoginDetails() {
		return userLoginDetails;
	}
	public void setUserLoginDetails(UserLoginDetails userLoginDetails) {
		this.userLoginDetails = userLoginDetails;
	}
	
	// CONSTRUCTORS
	
	// with the ID
	public User(long userId, Long companyId, UserLoginDetails userLoginDetails) {
		super();
		this.userId = userId;
		this.companyId = companyId;
		this.userLoginDetails = userLoginDetails;
	}
	
	// without the ID
	public User(Long companyId, UserLoginDetails userLoginDetails) {
		super();
		this.companyId = companyId;
		this.userLoginDetails = userLoginDetails;
	}
	
	// with user id and without userLoginDetails
	public User(long userId, Long companyId) {
		super();
		this.userId = userId;
		this.companyId = companyId;
	}
	
	
	public User(long userId, UserLoginDetails userLoginDetails) {
		super();
		this.userId = userId;
		this.userLoginDetails = userLoginDetails;
	}
	// empty constructor
	public User() {
		super();
	}
	
	
	// FUNCTION
	public String toString() {
		return " USER [userId:" + userId + ", companyId:" + companyId + ", UserLoginDetails:" + userLoginDetails + "]";
	}
	
}
