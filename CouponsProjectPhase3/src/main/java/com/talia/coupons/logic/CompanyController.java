package com.talia.coupons.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.talia.coupons.entities.Company;
import com.talia.coupons.dao.ICompaniesDao;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.utils.REGEX;

@Controller
public class CompanyController {

	@Autowired
	private ICompaniesDao companyDao;

	public long addCompany(Company company) throws ApplicationException {
		isCompanyValidToAdd(company);
		companyDao.save(company);
		return company.getId();
	}

	public Company getOneCompany(long companyId) throws ApplicationException {
		Optional<Company> optionalCompanyEntity = companyDao.findById(companyId);

		Company companyEntity = optionalCompanyEntity.get();

		return companyEntity;
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		return (List<Company>) companyDao.findAll();
	}

	public void updateCompany(Company company) throws ApplicationException {
		isCompanyValidToUpdate(company);
		companyDao.save(company);

	}

	@Transactional
	public void deleteCompany(long companyId) throws ApplicationException {
		companyDao.deleteById(companyId);
	}

//	public boolean isCompanyExists(long companyId) throws ApplicationException {
//		if (companyDao.isCompanyExistsById(companyId)) {
//			return true;
//		}
//		return false;
//	}

	private void isCompanyValidToAdd(Company company) throws ApplicationException {

		Company companyToCheckOn = new Company();

		if (company.getCompanyName() == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name cant be null");
		}
		if (company.getCompanyName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name is too short");
		}

		companyToCheckOn = companyDao.findByCompanyEmail(company.getCompanyEmail());
		if (companyToCheckOn != null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company email is Taken");
		}

		companyToCheckOn = companyDao.findByCompanyName(company.getCompanyName());
		if (companyToCheckOn != null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name is Taken");
		}

		if (!REGEX.isEmailValid(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company Email invalid");
		}

	

	}

	private void isCompanyValidToUpdate(Company company) throws ApplicationException {

		if (company.getCompanyName() == null || company.getCompanyName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company name needs to be at least 3 characters");
		}

		if (!REGEX.isEmailValid(company.getCompanyEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Company Email invalid");
		}

	}


}
