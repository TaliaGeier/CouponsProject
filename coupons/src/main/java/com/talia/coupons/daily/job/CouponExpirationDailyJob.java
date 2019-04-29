package com.talia.coupons.daily.job;

import java.util.ArrayList;
import java.util.TimerTask;

import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.exceptions.ApplicationException;

public class CouponExpirationDailyJob  extends TimerTask {

	
	private CouponsDao couponsDao;
	private PurchasesDao purchasesDao;

	//---------------------CTOR----------------------
	public CouponExpirationDailyJob() {
		this.couponsDao = new CouponsDao();
		this.purchasesDao = new PurchasesDao();
	}

	@Override
	public void run() {
		try {
			couponsDao.deleteAllExpiredCoupons();
			purchasesDao.deleteExpiredPurchases();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	
	}	

}
