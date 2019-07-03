package com.talia.coupons.interfaces;

import java.util.List;

import com.talia.coupons.beans.Purchases;
import com.talia.coupons.exceptions.ApplicationException;

public interface IPurchasesDao {

	long addCouponPurchase(Purchases purchase) throws ApplicationException;

	void updatePurchase(Purchases purchase) throws ApplicationException;

	void deletePurchase(long purchaseID) throws ApplicationException;

	void deleteExpiredPurchases() throws ApplicationException;

	void deletePurchasesByCustomerId(long customerId) throws ApplicationException;

	void deletePurchasesByCouponId(long couponId) throws ApplicationException;

	void deletePurchasesByCompanyId(long companyId) throws ApplicationException;

	Purchases getOnePurchase(long purchaseID) throws ApplicationException;

	List<Purchases> getAllPurchases() throws ApplicationException;

	List<Purchases> getPurchasesByCouponId(long couponId) throws ApplicationException;

	List<Purchases> getPurchasesByCompanyId(long companyId) throws ApplicationException;
	
	List<Purchases> getPurchasesByCystomerId(long customerId) throws ApplicationException;

	boolean isPurchaseExistsById(long purchaseId) throws ApplicationException;

}
