package com.talia.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talia.coupons.beans.Company;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.logic.CompanyController;


@RestController
@RequestMapping("/companies")
public class CompanyApi {
	
	@Autowired
	CompanyController companyController;
	
	@PostMapping
	public long addCompany(@RequestBody Company company) throws ApplicationException{
		return companyController.addCompany(company);
	}
	
	@GetMapping("/{companyId}")
	public Company getOneCompany(@PathVariable("companyId") long companyId) throws ApplicationException{
		
		return companyController.getOneCompany(companyId);
	}
	
	@GetMapping
	public  List<Company> getAllCompanies() throws ApplicationException{
		return companyController.getAllCompanies();
	}
	
	@PutMapping
	public void updateCompany(@RequestBody Company company) throws ApplicationException{
		companyController.updateCompany(company);
	}
	
	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long companyId) throws ApplicationException{
		companyController.deleteCompany(companyId);
	}
	
	
}
