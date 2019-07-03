package com.talia.coupons.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.talia.coupons.dao.ICompaniesDao;
import com.talia.coupons.dao.ICouponsDao;
import com.talia.coupons.dao.IPurchasesDao;
import com.talia.coupons.entities.Company;
import com.talia.coupons.entities.Coupon;

import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class CouponController {

	@Autowired
	private ICouponsDao couponDao;
	@Autowired
	private IPurchasesDao purchaseDao;
	@Autowired
	private ICompaniesDao companyDao;

	public long addCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToAdd(coupon);
		couponDao.save(coupon);
		return coupon.getCouponId();
	}

	public Coupon getOneCoupon(long couponId) throws ApplicationException {
		Optional<Coupon> optionalCouponEntity = couponDao.findById(couponId);

		Coupon couponEntity = optionalCouponEntity.get();

		return couponEntity;

	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		return (List<Coupon>) couponDao.findAll();
	}

	public List<Coupon> getAllCouponsByMaxPrice(Double maxPrice) throws ApplicationException {

		return couponDao.getAllCouponsByPrice(maxPrice);
	}

	public List<Coupon> getAllCouponsByCategory(String category) throws ApplicationException {

		return couponDao.getAllCouponsByCategory(category);
	}

	public List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException {
		return couponDao.getAllCouponsByCompany(companyId);

	}


	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {
		
		return couponDao.getAllCouponsByCustomer(customerId);
	}

	

	public void updateCoupon(Coupon coupon) throws ApplicationException {
		isCouponValidToUpdate(coupon);
		couponDao.save(coupon);
	}

		@Transactional
		public void deleteCoupon(long couponId) throws ApplicationException {
			purchaseDao.deletePurchasesByCouponId(couponId);
			couponDao.deleteById(couponId);
		}

	


	private void isCouponValidToAdd(Coupon coupon) throws ApplicationException {

//		if (companyController.getOneCompany(coupon.getCompanyId()) == null) {
//			throw new ApplicationException(ErrorType.INVALID_INPUT,
//					"Company: " + coupon.getCompanyId() + " was not found while trying to add a coupon");
//		}

		validateCompanyExists(coupon.getCompany());

		validateCouponTitle(coupon.getTitle());

		validateCouponDescription(coupon.getDescription());

		validateCouponAmount(coupon.getAmount());

		validateCouponPrice(coupon.getPrice());

		validateCouponDate(coupon.getStart_date(), coupon.getEnd_date());

//		if (couponDao.isTitleExists(coupon.getCompanyId(), coupon.getTitle())) {
//			throw new ApplicationException(ErrorType.DUPLICATED_ENTRY, "Coupon Title is Taken in this company.");
//		}

	}

	private void isCouponValidToUpdate(Coupon coupon) throws ApplicationException {

		if (!couponDao.existsById(coupon.getCouponId())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Coupon was not found while trying to update it");
		}

		validateCompanyExists(coupon.getCompany());

		validateCouponTitle(coupon.getTitle());

		validateCouponDescription(coupon.getDescription());

		validateCouponAmount(coupon.getAmount());

		validateCouponPrice(coupon.getPrice());

		validateCouponDate(coupon.getStart_date(), coupon.getEnd_date());

		validateCouponCompanyIdUnchanged(coupon);

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
	
	private void validateCompanyExists(Company company) throws ApplicationException {

		if (company == null) {
			throw new ApplicationException(ErrorType.INSERTION_ERROR,
					"Failed to find company while adding a coupon to it.");
		}
		if (!companyDao.existsById(company.getId())) {
			throw new ApplicationException(ErrorType.INSERTION_ERROR,
					"Failed to find company while adding a coupon to it.");
		}
	}
	private void validateCouponCompanyIdUnchanged(Coupon coupon) throws ApplicationException {
		Optional<Company> optionalCompanyEntity = companyDao.findById(coupon.getCompany().getId());

		Company companyEntity = optionalCompanyEntity.get();

		if (companyEntity.getId() != coupon.getCompany().getId()) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "Cant update coupon's company ID");
		}
	}
}
