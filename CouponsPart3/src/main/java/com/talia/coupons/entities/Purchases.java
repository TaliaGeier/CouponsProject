package com.talia.coupons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//initiating table
@Entity
@Table(name = "purchases")
public class Purchases {
	
	@Id
	@GeneratedValue
	@Column(name = "purchase_id", unique = true, nullable = false)
	private long purchaseId;
	
	
	
	@Column(name = "purchase_amount", nullable = false)
	private int amount;
	
	@ManyToOne
	private Coupon coupon;

	@ManyToOne
	private Customer customer;
	
	public Purchases() {
		super();
	}
	
	
	
	public long getPurchaseId() {
		return purchaseId;
	}



	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}



	public int getAmount() {
		return amount;
	}



	public void setAmount(int amount) {
		this.amount = amount;
	}



	public Coupon getCoupon() {
		return coupon;
	}



	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}





}
