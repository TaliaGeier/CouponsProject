package com.talia.coupons.beans;

import com.talia.coupons.enums.ClientType;

public class UserData {
	private long userId;
	private Long companyId;
	private ClientType type;

	public UserData(long userId, Long companyId, ClientType type) {
		this.userId = userId;
		this.companyId = companyId;
		this.type = type;
	}

	public UserData() {
	}

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

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}


}
