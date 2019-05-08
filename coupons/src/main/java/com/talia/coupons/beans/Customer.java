package com.talia.coupons.beans;

public class Customer {

	// ----------------------ATTRIBUTES----------------------
	private long customerId;
	
	private String customerFirstName;
	private String customerLastName;
	private User user;
	// ----------------------Get & Set----------------------
	public long getCustomerId() {
		return customerId;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}




	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	// ----------------------Constructors----------------------

	
	// With the id attribute

	public Customer(long customerId, String customerFirstName, String customerLastName) {
		super();
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
	}
	

	// Without the id attribute
	
	public Customer(String customerFirstName, String customerLastName) {
		super();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
	}

	// Empty Constructor

	public Customer() {
	}


	// without customer id and with user 
	public Customer( String customerFirstName, String customerLastName, User user) {
		super();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.user = user;
	}



	// ----------------------Functions----------------------
	@Override
	public String toString() {
		return "CUSTOMER [Last Name:" + customerLastName + ", First Name:" + customerFirstName + ", ID:" + customerId + "]";
	}

}
