package com.talia.coupons.interfaces;

import java.util.List;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.enums.Category;
import com.talia.coupons.exceptions.ApplicationException;


public interface ICouponsDao {

	long addCoupon(Coupon coupon) throws ApplicationException;

	void updateCoupon(Coupon coupon) throws ApplicationException;

	void deleteCoupon(long couponID) throws ApplicationException;

	void deleteAllCompanyCoupons(long companyId) throws ApplicationException;

	Coupon getOneCoupon(long couponID) throws ApplicationException;

	List<Coupon> getAllCoupons() throws ApplicationException;

	List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException;

	List<Coupon> getAllCouponsByMaxPrice(Double price) throws ApplicationException;

	List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException;

	List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException;

	List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException;

	List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException;

	List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException;

	List<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws ApplicationException;

	boolean isTitleExists(long companyId, String title) throws ApplicationException;

	boolean isCouponExpired(Coupon coupon) throws ApplicationException;


	void deleteAllExpiredCoupons() throws ApplicationException;

}