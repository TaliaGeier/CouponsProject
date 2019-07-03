package com.talia.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// initiating table

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@Column(name = "customer_id")
	private long customerId;
	
	@Column(name = "customer_first_name", nullable = false)
	private String customerFirstName;
	
	@Column(name = "customer_last_name", nullable = false)
	private String customerLastName;
	
	@JoinColumn
	@MapsId
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Purchases> purchases;
	
	


	public long getCustomerId() {
		return customerId;
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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public List<Purchases> getPurchases() {
		return purchases;
	}



	public void setPurchases(List<Purchases> purchases) {
		this.purchases = purchases;
	}



	public Customer() {
		this.user = new User();
	}



}
