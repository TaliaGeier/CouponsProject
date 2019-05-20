package com.talia.coupons.beans;

import com.talia.coupons.enums.ClientType;

public class UserDataMap {
	
	private long userID;
	private long companyID;
	private ClientType type;
	
	
	public UserDataMap(long userID, long companyID, ClientType type) {
		super();
		this.userID = userID;
		this.companyID = companyID;
		this.type = type;
	}
	
	


	public UserDataMap(long companyID, ClientType type) {
		super();
		this.companyID = companyID;
		this.type = type;
	}




	public long getUserID() {
		return userID;
	}


	public void setUserID(long userID) {
		this.userID = userID;
	}


	public long getCompanyID() {
		return companyID;
	}


	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}


	public ClientType getType() {
		return type;
	}


	public void setType(ClientType type) {
		this.type = type;
	}
	
	

}
