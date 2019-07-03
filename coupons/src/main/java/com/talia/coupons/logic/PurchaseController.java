package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.PurchasesDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Purchases;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class PurchaseController {
	
	@Autowired
	private PurchasesDao purchaseDao;
	
	@Autowired
	private CouponController couponController;
	
	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private CompanyController companyController;
	
	

	public long addPurchase(Purchases purchase) throws ApplicationException {
		addPurchaseLogic(purchase);
		return purchaseDao.addCouponPurchase(purchase);

	}
	
	public Purchases getOnePurchase(long purchaseId) throws ApplicationException {
		
		if(purchaseDao.isPurchaseExistsById(purchaseId)) {
			return purchaseDao.getOnePurchase(purchaseId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR,
				"Failed to get purchase");
		
	}

	public List<Purchases> getAllPurchases() throws ApplicationException {

		return purchaseDao.getAllPurchases();
	}
	
	public List<Purchases> getPurchasesByCystomerId(long customerId) throws ApplicationException {

		return purchaseDao.getPurchasesByCystomerId(customerId);
	}

	public void deletePurchase(long purchaseID) throws ApplicationException {
		
		if(purchaseDao.isPurchaseExistsById(purchaseID)) {
			purchaseDao.deletePurchase(purchaseID);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR,
				"Failed to delete purchase");
	}

	public void deleteExpiredPurchases() throws ApplicationException {
		purchaseDao.deleteExpiredPurchases();
	}
	public void deletePurchasesByCustomerId (long customerId)throws ApplicationException {
		if (customerController.isCustomerExists(customerId)) {
			purchaseDao.deletePurchasesByCustomerId(customerId);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR,
				"Failed to delete purchases");
	} 
	
	public void deletePurchasesByCompanyId (long companyId)throws ApplicationException {
		if (companyController.isCompanyExists(companyId)) {
			purchaseDao.deletePurchasesByCompanyId(companyId);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR,
				"Failed to delete purchases");
	} 
	public void deletePurchasesByCouponId (long couponID)throws ApplicationException {
		if (couponController.isCouponExists(couponID)) {
			purchaseDao.deletePurchasesByCouponId(couponID);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR,
				"Failed to delete purchases");
	}
	
	private void addPurchaseLogic(Purchases purchase) throws ApplicationException {

		if (couponController.getOneCoupon(purchase.getCouponId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT,
					"coupon number : " + purchase.getCouponId() + " was not found.");
		}

		if (purchaseDao.isPurchaseExistsById(purchase.getPurchaseId())) {
			throw new ApplicationException(ErrorType.READ_ERROR,
					"Purchase : " + purchase.getPurchaseId() + " already exist.");
		}

		if (couponController.getOneCoupon(purchase.getCouponId()).getAmount() - purchase.getAmount() < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "not enough coupons in stock");
		}

		Coupon couponBought = couponController.getOneCoupon(purchase.getCouponId());
		couponBought.setAmount(couponBought.getAmount() - purchase.getAmount());
		couponController.updateCoupon(couponBought);

	}
}
