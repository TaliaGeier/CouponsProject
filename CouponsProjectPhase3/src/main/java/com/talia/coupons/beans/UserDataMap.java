package com.talia.coupons.beans;

import com.talia.coupons.enums.ClientType;

/**
 * @author talia_ngnice3
 *
 */
public class UserDataMap {
	
	private long userID;
	private Long companyID;
	private ClientType type;
	private int token;
	
	
	
	
	public UserDataMap(long userID, Long companyID, ClientType type, int token) {
		super();
		this.userID = userID;
		this.companyID = companyID;
		this.type = type;
		this.token = token;
	}




	public int getToken() {
		return token;
	}




	public void setToken(int token) {
		this.token = token;
	}




	public UserDataMap(long userID, Long companyID, ClientType type) {
		super();
		this.userID = userID;
		this.companyID = companyID;
		this.type = type;
	}
	
	


	public UserDataMap(Long companyID, ClientType type) {
		super();
		this.companyID = companyID;
		this.type = type;
	}




	public UserDataMap(long userID, ClientType type, int token) {
		super();
		this.userID = userID;
		this.type = type;
		this.token = token;
	}




	public UserDataMap() {
		super();
	}




	public long getUserID() {
		return userID;
	}


	public void setUserID(long userID) {
		this.userID = userID;
	}


	public Long getCompanyID() {
		return companyID;
	}


	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}


	public ClientType getType() {
		return type;
	}


	public void setType(ClientType type) {
		this.type = type;
	}
	
	

}
