package com.talia.coupons.logic;

import java.util.List;

import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Purchases;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

public class PurchaseController {
	private PurchasesDao purchaseDao;
	private CouponsDao couponDao;
	
	
	
	public PurchaseController(PurchasesDao purchaseDao, CouponsDao couponDao) {
		super();
		this.purchaseDao = purchaseDao;
		this.couponDao = couponDao;
	}

	public long addPurchase(Purchases purchase) throws ApplicationException {
		addPurchaseLogic(purchase);
		return purchaseDao.addCouponPurchase(purchase);

	}
	
	public Purchases getOnePurchase(long purchaseId) throws ApplicationException {

		return purchaseDao.getOnePurchase(purchaseId);
	}

	public List<Purchases> getAllPurchases() throws ApplicationException {

		return purchaseDao.getAllPurchases();
	}

	public void deletePurchase(long purchaseToDelete) throws ApplicationException {
		purchaseDao.deletePurchase(purchaseToDelete);
	}

	public void deleteExpiredPurchases() throws ApplicationException {
		purchaseDao.deleteExpiredPurchases();
	}
	
	private void addPurchaseLogic(Purchases purchase) throws ApplicationException {

		if (couponDao.getOneCoupon(purchase.getCouponId()) == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT,
					"coupon number : " + purchase.getCouponId() + " was not found.");
		}

		if (purchaseDao.isPurchaseExistsById(purchase.getPurchaseId())) {
			throw new ApplicationException(ErrorType.READ_ERROR,
					"Purchase : " + purchase.getPurchaseId() + " already exist.");
		}

		if (couponDao.getOneCoupon(purchase.getCouponId()).getAmount() - purchase.getAmount() < 0) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "not enough coupons in stock");
		}

		Coupon couponBought = couponDao.getOneCoupon(purchase.getCouponId());
		couponBought.setAmount(couponBought.getAmount() - purchase.getAmount());
		couponDao.updateCoupon(couponBought);

	}
}
