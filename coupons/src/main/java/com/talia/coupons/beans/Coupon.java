package com.talia.coupons.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.talia.coupons.enums.Category;

/**
 * Coupon - this class represents the attributes and functions necessary to
 * create a coupon.
 */
public class Coupon {

	// ----------------------ATTRIBUTES----------------------

	private long couponId;
	private long companyId;
	private Category category;
	private String title;
	private String description;
	private Date start_date;
	private Date end_date;
	private int amount;
	private double price;
	private String img;

	// ----------------------Get & Set----------------------

	public long getCouponId() {
		return couponId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public Category getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public int getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}

	public String getImg() {
		return img;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Coupon(long couponId, long companyId, Category category, String title, String description, Date start_date,
			Date end_date, int amount, double price, String img) {
		super();
		this.couponId = couponId;
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}

	public Coupon(long companyId, Category category, String title, String description, Date start_date, Date end_date,
			int amount, double price, String img) {
		super();
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}
	
	// without coupon id and with String as date type
	public Coupon(long companyId, Category category, String title, String description, String start_date, String end_date,
			int amount, double price, String img) throws ParseException {
		super();
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		java.util.Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
		java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
		this.start_date = startDate;
		this.end_date = endDate;
		this.amount = amount;
		this.price = price;
		this.img = img;
	}
	

	public Coupon() {
		super();
	}

	@Override
	public String toString() {
		return "Coupon [id=" + couponId + ", companyId=" + companyId + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", start_date=" + start_date + ", end_date=" + end_date + ", amount="
				+ amount + ", price=" + price + ", img=" + img + "]";
	}

}
