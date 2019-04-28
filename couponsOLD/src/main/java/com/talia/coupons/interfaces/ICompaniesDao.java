package com.talia.coupons.interfaces;

import java.util.List;

import com.talia.coupons.beans.Company;
import com.talia.coupons.exceptions.ApplicationException;

public interface ICompaniesDao {

	long addCompany(Company company) throws ApplicationException;

	void updateCompany(Company company) throws ApplicationException;

	void deleteCompany(long companyID) throws ApplicationException;

	List<Company> getAllCompanies() throws ApplicationException;

	Company getOneCompany(long companyID) throws ApplicationException;

	String getCompanyDetails(long companyId) throws ApplicationException;

	boolean isCompanyExistsById(long companyId) throws ApplicationException;

	boolean isCompanyExistsByName(String companyName) throws ApplicationException;

	boolean isCompanyExistsByEmail(String email) throws ApplicationException;

}