package com.talia.coupons.dao;



import com.talia.coupons.entities.Company;

import org.springframework.data.repository.CrudRepository;

public interface ICompaniesDao extends CrudRepository<Company, Long> {

	public Company findByCompanyEmail(String companyEmail);

	public Company findByCompanyName(String companyName);

	

}
