package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.ICouponsDao;
import com.talia.coupons.dao.IPurchasesDao;
import com.talia.coupons.entities.Coupon;
import com.talia.coupons.entities.Purchases;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class PurchaseController {
	
	@Autowired
	private IPurchasesDao purchaseDao;
	@Autowired
	private ICouponsDao couponDao;

	public long addPurchase(Purchases purchase) throws ApplicationException {
		addPurchaseLogic(purchase);
		purchaseDao.save(purchase);
		return purchase.getPurchaseId();
	}

	public Purchases getOnePurchase(long purchaseId) throws ApplicationException {

		return purchaseDao.findById(purchaseId).get();
	}

	public List<Purchases> getAllPurchases() throws ApplicationException {

		return (List<Purchases>) purchaseDao.findAll();
	}

	public List<Purchases> getPurchasesByCustomerId(long customerId) throws ApplicationException {

		return purchaseDao.getPurchasesByCustomerId(customerId);
	}

	public List<Purchases> getPurchasesByCustomerIdAndCouponId(long customerId, long couponId)
			throws ApplicationException {

		return purchaseDao.getPurchasesByCustomerIdAndCouponId(customerId, couponId);
	}

	public void deletePurchase(long purchaseToDelete) throws ApplicationException {
		purchaseDao.deleteById(purchaseToDelete);
	}

	private void addPurchaseLogic(Purchases purchase) throws ApplicationException {

		if (couponDao.findById(purchase.getCoupon().getCouponId()).get() == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Coupon was not found.");
		}

		if (purchase.getAmount() < 1) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Purchase amount needs to be 1 or higher");
		}

		if (couponDao.findById(purchase.getCoupon().getCouponId()).get().getAmount()
				- purchase.getAmount() < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "not enough coupons in stock");
		}

		Coupon couponBought = couponDao.findById(purchase.getCoupon().getCouponId()).get();
		couponBought.setAmount(couponBought.getAmount() - purchase.getAmount());
		couponDao.save(couponBought);

	}

}