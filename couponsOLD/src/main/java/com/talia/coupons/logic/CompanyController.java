package com.talia.coupons.logic;


import java.util.List;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.*;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
//import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import com.talia.coupons.utils.REGEX;


public class CompanyController {
	private CompaniesDao companyDao;
	private CouponsDao couponDao;
	private UsersDao userDao;
	private PurchasesDao purchaseDao;
	
	
	public CompanyController(CompaniesDao companyDao) {
		super();
		this.companyDao = companyDao;
	}
	
	public long addCompany(Company company) throws ApplicationException {
		isCompanyValidToAdd(company);
		return companyDao.addCompany(company);
	}

	public Company getOneCompany(long companyId) throws ApplicationException {
		return companyDao.getOneCompany(companyId);
	}
	
	public List<Company> getAllCompanies() throws ApplicationException {
		return companyDao.getAllCompanies();
	}
	
	public void updateCompany(Company company) throws ApplicationException {
		isCompanyValidToUpdate(company);
		companyDao.updateCompany(company);

	}
	
	public void deleteCompany(long companyId) throws ApplicationException {
		deleteCompanyLogic(companyId);
	}
	
	private void isCompanyValidToAdd(Company company) throws ApplicationException {

		if (company.getName() == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name cant be null");
		}
		if (company.getName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name is too short");
		}
		if (companyDao.isCompanyExistsByEmail(company.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company email is Taken");
		}

		if (companyDao.isCompanyExistsByName(company.getName())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name is Taken");
		}

		if (!REGEX.isEmailValid(company.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company Email invalid");
		}

	}

	private void isCompanyValidToUpdate(Company company) throws ApplicationException {

		if (company.getName() == null || company.getName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name needs to be at least 3 characters");
		}

		if (!REGEX.isEmailValid(company.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company Email invalid");
		}

	}
	
	private void deleteCompanyLogic(long companyId) throws ApplicationException {

		purchaseDao.deletePurchasesByCompanyId(companyId);

		couponDao.deleteAllCompanyCoupons(companyId);

		userDao.deleteUsersByCompanyId(companyId);

		companyDao.deleteCompany(companyId);
	}
}
