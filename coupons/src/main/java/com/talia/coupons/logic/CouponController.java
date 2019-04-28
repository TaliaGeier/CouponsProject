package com.talia.coupons.logic;

import java.util.Date;
import java.util.List;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.enums.Category;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

public class CouponController {

	private CouponsDao couponDao;
	private PurchasesDao purchaseDao;
	private CompaniesDao companyDao;
	
	public long addCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToAdd(coupon);
		return couponDao.addCoupon(coupon);
	}
	public Coupon getOneCoupon(long couponId) throws ApplicationException {

		return couponDao.getOneCoupon(couponId);
	}
	public List<Coupon> getAllCoupons() throws ApplicationException {

		return couponDao.getAllCoupons();
	}
	
	public List<Coupon> getAllCouponsByMaxPrice(Double price) throws ApplicationException {

		return couponDao.getAllCouponsByMaxPrice(price);
	}
	
	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {

		return couponDao.getAllCouponsByCategory(category);
	}
	public List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException {

		return couponDao.getCompanyCoupons(companyId);
	}
	public List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException {

		return couponDao.getCompanyCouponsByCategory(companyId, category);
	}
	public List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException {

		return couponDao.getCompanyCouponsByMaxPrice(companyId, maxPrice);
	}
	
	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {

		return couponDao.getCustomerCoupons(customerId);
	}
	
	public List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException {

		return couponDao.getCustomerCouponsByCategory(customerId, category);
	}
	
	public List<Coupon> getCustomerCouponsByMaxPrice(long customerId, Double maxPrice) throws ApplicationException {

		return couponDao.getCustomerCouponsByMaxPrice(customerId, maxPrice);
	}
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToUpdate(coupon);
		couponDao.updateCoupon(coupon);
	}

	public void deleteCoupon(long couponId) throws ApplicationException {
		purchaseDao.deletePurchasesByCouponId(couponId);
		couponDao.deleteCoupon(couponId);
	}
	private void isCouponValidToAdd(Coupon coupon) throws ApplicationException {

		if (companyDao.getOneCompany(coupon.getCompanyId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT,
					"Company: " + coupon.getCompanyId() + " was not found while trying to add a coupon");
		}

		validateCouponTitle(coupon.getTitle());

		validateCouponDescription(coupon.getDescription());

		validateCouponAmount(coupon.getAmount());

		validateCouponPrice(coupon.getPrice());

		validateCouponDate(coupon.getStart_date(), coupon.getEnd_date());

		if (couponDao.isTitleExists(coupon.getCompanyId(), coupon.getTitle())) {
			throw new ApplicationException(ErrorType.DUPLICATED_ENTRY, "Coupon Title is Taken in this company.");
		}

	}
	
	private void isCouponValidToUpdate(Coupon coupon) throws ApplicationException {

		if (companyDao.getOneCompany(coupon.getCompanyId()) == null
				|| couponDao.getOneCoupon(coupon.getCouponId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT,
					"Company: " + coupon.getCompanyId() + " was not found while trying to add a coupon");
		}

		validateCouponTitle(coupon.getTitle());

		validateCouponDescription(coupon.getDescription());

		validateCouponAmount(coupon.getAmount());

		validateCouponPrice(coupon.getPrice());

		validateCouponDate(coupon.getStart_date(), coupon.getEnd_date());

		if (couponDao.getOneCoupon(coupon.getCouponId()).getCompanyId() != coupon.getCompanyId()) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "cant update coupons company id");
		}

	}
	private void validateCouponTitle(String title) throws ApplicationException {
		if (title.length() < 5) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon is title is too short.");
		}
	}

	private void validateCouponDescription(String description) throws ApplicationException {
		if (description.length() < 5) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon is description is too short.");
		}
	}

	private void validateCouponAmount(int amount) throws ApplicationException {
		if (amount < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon amount cant be less than 0.");
		}
	}

	private void validateCouponPrice(Double price) throws ApplicationException {
		if (price < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon price cant be 0 or under.");
		}
	}

	private void validateCouponDate(Date startDate, Date endDate) throws ApplicationException {

		Date currentDate = new Date();

		if (endDate.after(startDate) && endDate.after(currentDate)) {
			return;
		}

		throw new ApplicationException(ErrorType.INVALID_INPUT, "invalid coupon date");

	}
}

