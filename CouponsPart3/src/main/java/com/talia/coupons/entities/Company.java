package com.talia.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// initiating table
@Entity
@Table(name="Companies")
public class Company {

	
//setting up the table
	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private long id;
	
	@Column(name = "company_name", unique = true, nullable = false, length = 30)
	private String companyName;
	
	@Column(name = "company_email", unique = true, nullable = false, length = 30)
	private String companyEmail;

//mapping 
	@OneToMany(mappedBy = "company", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Coupon> companyCoupons;

	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<User> users;

	
	// ----------------------Constructors----------------------


	public Company() {
	}

	
	// ----------------------GETTERS & SETTERS----------------------

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



	public List<Coupon> getCompanyCoupons() {
		return companyCoupons;
	}



	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}



	




}
