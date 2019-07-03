package com.talia.coupons.beans;

public class Purchases {
	
	// ATTRIBUTES
	
	private long purchaseId;
	private long customerId;
	private long couponId;
	private int amount;
	
	// GETTERS AND SETTERS
	public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	// CONSTRUCTOR
	public Purchases(long purchaseId, long customerId, long couponId, int amount) {
		super();
		this.purchaseId = purchaseId;
		this.customerId = customerId;
		this.couponId = couponId;
		this.amount = amount;
	}
	// without purchaseID
	public Purchases(long customerId, long couponId, int amount) {
		super();
		this.customerId = customerId;
		this.couponId = couponId;
		this.amount = amount;
	}
	
	// empty constructor
	public Purchases() {
		super();
	}
	
	// ----------------------Functions----------------------
	@Override
	public String toString() {
		return "PURCHASE [Purchase ID:" + purchaseId + ", Customer ID:" + customerId + ", Coupon ID:" + couponId + ", Amount:" + amount + "]";
	}


}
