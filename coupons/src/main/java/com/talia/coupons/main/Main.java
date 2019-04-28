package com.talia.coupons.main;

import com.talia.coupons.dao.CompaniesDao;

import beans.Company;
import exceptions.ExEmail;
import exceptions.ExNameLength;
import exceptions.ExNotNull;
import exceptions.ExPassword;
import interfaces.ICompaniesDao;

public class Main {

	public static void main(String[] args) {

//		Test.testAll();
	
		try {

			ICompaniesDao test = new CompaniesDao();

			Company company;
			
			company = new Company("Aa13467912", "Nil9711@gmail.com", "Nil");

//			test.addCompany(company);
//			System.out.println(company.getId());
			
			
		} catch (ExNameLength e) {
			System.out.println(e.getMessage());
		} catch (ExNotNull e) {
			System.out.println(e.getMessage());
		} catch (ExEmail e) {
			System.out.println(e.getMessage());
		} catch (ExPassword e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
