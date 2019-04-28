package com.talia.coupons.main;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;

public class Testing2 {

	public static void main(String[] args) {
		CouponsDao couponsDao = new CouponsDao();
		CompaniesDao companiesDao = new CompaniesDao();
		try {
//			couponsDao.getOneCoupon(1);
//			System.out.println(companiesDao.getOneCompany(1));
			companiesDao.addCompany(company)
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
