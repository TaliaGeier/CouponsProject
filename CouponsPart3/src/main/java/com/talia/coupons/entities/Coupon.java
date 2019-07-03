package com.talia.coupons.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//initiating table
/**
 * @author talia_ngnice3
 *
 */
@Entity
@Table(name = "coupons")
public class Coupon {

	// setting up table
	@Id
	@GeneratedValue
	@Column(name = "coupon_id")
	private long couponId;

	@Column(name = "coupon_category", nullable = false, length = 20)
	private String category;

	@Column(name = "coupon_title", nullable = false, length = 20)
	private String title;

	@Column(name = "coupon_description", nullable = false, length = 50)
	private String description;

	@Column(name = "coupon_start_date", nullable = false)
	private Date start_date;

	@Column(name = "coupon_end_date", nullable = false)
	private Date end_date;

	@Column(name = "coupon_amount", nullable = false)
	private int amount;

	@Column(name = "coupon_price", nullable = false)
	private double price;

	@Column(name = "coupon_image", nullable = true)
	private String img;

	// mapping
	@ManyToOne
	
	private Company company;

	@OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Purchases> couponPurchases;

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Purchases> getCouponPurchases() {
		return couponPurchases;
	}

	public void setCouponPurchases(List<Purchases> couponPurchases) {
		this.couponPurchases = couponPurchases;
	}

	public Coupon() {
		super();
	}

}
