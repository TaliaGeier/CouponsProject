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

	public String getName() {
		return companyName;
	}

	public String getEmail() {
		return companyEmail;
	}

	public void setEmail(String email) {
		this.companyEmail = email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.companyName = name;
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
