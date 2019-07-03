package com.talia.coupons.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Customer;
import com.talia.coupons.enums.Category;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class CouponController {

	@Autowired
	private CouponsDao couponDao;
	
	@Autowired
	private PurchaseController purchaseController;
	
	@Autowired
	private CompanyController companyController;
	
	@Autowired
	private CustomerController customerController;

	public long addCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToAdd(coupon);
		return couponDao.addCoupon(coupon);
	}

	public Coupon getOneCoupon(long couponId) throws ApplicationException {
		if (isCouponExists(couponId)) {
			return couponDao.getOneCoupon(couponId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupon");

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
		if(companyController.isCompanyExists(companyId)) {
			return couponDao.getCompanyCoupons(companyId);
		}

		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}

	public List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException {

		
		if(companyController.isCompanyExists(companyId)) {
			return couponDao.getCompanyCouponsByCategory(companyId, category);
		}

		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException {

		
		if(companyController.isCompanyExists(companyId)) {
			return couponDao.getCompanyCouponsByMaxPrice(companyId, maxPrice);
		}

		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}

	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {
		
		if(customerController.isCustomerExists(customerId)) {
			return couponDao.getCustomerCoupons(customerId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}

	public List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException {

	
		if(customerController.isCustomerExists(customerId)) {
			return couponDao.getCustomerCouponsByCategory(customerId, category);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}

	public List<Coupon> getCustomerCouponsByMaxPrice(long customerId, Double maxPrice) throws ApplicationException {

		
		if(customerController.isCustomerExists(customerId)) {
			return couponDao.getCustomerCouponsByMaxPrice(customerId, maxPrice);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get coupons");
	}


	public void updateCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToUpdate(coupon);
		couponDao.updateCoupon(coupon);
	}

	public void deleteCoupon(long couponId) throws ApplicationException {
		if(isCouponExists(couponId)) {
			try {
			purchaseController.deletePurchasesByCouponId(couponId);
			}catch(ApplicationException e) {
				e.printStackTrace();
			}
			finally {
			couponDao.deleteCoupon(couponId);
			}
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete coupon");
	}

	public void deleteAllCompanyCoupons(long companyID) throws ApplicationException {
		if (companyController.isCompanyExists(companyID)) {
			couponDao.deleteAllCompanyCoupons(companyID);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete all company's coupons");
	}

	public boolean isCouponExists(long couponID) throws ApplicationException {
		if (couponDao.isCouponExistsById(couponID)) {
			return true;
		}
		return false;
	}

	private void isCouponValidToAdd(Coupon coupon) throws ApplicationException {

		if (companyController.getOneCompany(coupon.getCompanyId()) == null) {
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

		if (companyController.getOneCompany(coupon.getCompanyId()) == null
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
		if (title.length() < 3) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon title is too short.");
		}
	}

	private void validateCouponDescription(String description) throws ApplicationException {
		if (description.length() < 5) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon description is too short.");
		}
	}

	private void validateCouponAmount(int amount) throws ApplicationException {
		if (amount < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon amount can't be less than 0.");
		}
	}

	private void validateCouponPrice(Double price) throws ApplicationException {
		if (price < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "coupon price can't be 0 or under.");
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
