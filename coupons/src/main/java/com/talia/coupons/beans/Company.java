package com.talia.coupons.beans;

public class Company {

	// ----------------------ATTRIBUTES----------------------
	private long id;
	private String companyName;
	private String companyEmail;

	// ----------------------Get & Set----------------------


	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	
	// ----------------------Constructors----------------------

		// With the id attribute
		public Company(long id, String email, String name) {
			super();
			this.id = id;
			this.companyEmail = email;
			this.companyName = name;
		}
	// Without the id attribute
	public Company(String email, String name) {
		super();
		this.companyEmail = email;
		this.companyName = name;
	}

	public Company() {
	}

	// ----------------------Functions----------------------
	@Override
	public String toString() {
		return "COMPANY [email:" + companyEmail + ", name:" + companyName + ", id:" + id + "]";
	}

}
