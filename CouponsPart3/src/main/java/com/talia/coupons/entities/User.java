package com.talia.coupons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.talia.coupons.enums.ClientType;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "user_id", unique = true, nullable = false)
	private long userId;
	
	
	@Column(name = "user_email", unique = true, nullable = false)
	private String userName;

	@Column(name = "user_password", nullable = false)
	private String userPassword;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", nullable = false)
	private ClientType userType;

	@ManyToOne
	private Company company;
	
	
	
	
	

	
	
	public long getUserId() {
		return userId;
	}








	public void setUserId(long userId) {
		this.userId = userId;
	}








	public String getUserName() {
		return userName;
	}








	public void setUserName(String userName) {
		this.userName = userName;
	}








	public String getUserPassword() {
		return userPassword;
	}








	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}








	public ClientType getUserType() {
		return userType;
	}








	public void setUserType(ClientType userType) {
		this.userType = userType;
	}








	public Company getCompany() {
		return company;
	}








	public void setCompany(Company company) {
		this.company = company;
	}








	public User() {
		super();
	}
	
	
	
	
}
