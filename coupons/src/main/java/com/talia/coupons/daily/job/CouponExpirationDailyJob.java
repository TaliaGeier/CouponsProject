package com.talia.coupons.daily.job;

import java.util.ArrayList;

import com.talia.coupons.dao.CouponsDao;

public class CouponExpirationDailyJob implements Runnable {

	
	public CouponExpirationDailyJob() {
	}
	
	
	@Override
	public void run() {

		while (true) {
			CouponsDao CouponsDBDAO = new CouponsDao();
			System.out.println("DailyJob is starting");
			try {
				synchronized (CouponsDBDAO.getAllExpiredCouponsById()) {
					ArrayList<Long> list = CouponsDBDAO.getAllExpiredCouponsById();
					while (list.size() > 0) {
						CouponsDBDAO.deleteAllCouponPurchasesByCouponId(list.get(0));
						CouponsDBDAO.deleteCoupon(list.get(0));
						list.remove(0);
						System.out.println("coupons deleted");
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			try {
				Thread.sleep(1000 * 60 * 60 * 24);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * stop - stops the thread.
	 */
	public void stop() {
		
		System.out.println("Daily job has stopped");

		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

}
