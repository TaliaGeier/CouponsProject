package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.*;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.utils.REGEX;

@Controller
public class CompanyController {

	@Autowired
	private CompaniesDao companyDao;

	@Autowired
	private CouponController couponController;

	@Autowired
	private UserController userController;

	@Autowired
	private PurchaseController purchaseController;

	public long addCompany(Company company) throws ApplicationException {
		isCompanyValidToAdd(company);
		return companyDao.addCompany(company);
	}

	public Company getOneCompany(long companyId) throws ApplicationException {
		if (isCompanyExists(companyId)) {
			return companyDao.getOneCompany(companyId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get company");
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		return companyDao.getAllCompanies();
	}

	public void updateCompany(Company company) throws ApplicationException {
		isCompanyValidToUpdate(company);
		companyDao.updateCompany(company);

	}

	public void deleteCompany(long companyId) throws ApplicationException {
		if (isCompanyExists(companyId)) {
			deleteCompanyLogic(companyId);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete company");
	}

	public boolean isCompanyExists(long companyId) throws ApplicationException {
		if (companyDao.isCompanyExistsById(companyId)) {
			return true;
		}
		return false;
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

		purchaseController.deletePurchasesByCompanyId(companyId);

		couponController.deleteAllCompanyCoupons(companyId);

		userController.deleteUsersByCompanyId(companyId);

		companyDao.deleteCompany(companyId);

	}
}
