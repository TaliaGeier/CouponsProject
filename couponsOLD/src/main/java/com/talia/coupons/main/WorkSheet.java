package com.talia.coupons.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.talia.coupons.dao.CompaniesDao;

import beans.Company;
import exceptions.ExEmail;
import exceptions.ExNameLength;
import exceptions.ExNotNull;
import exceptions.ExPassword;
import interfaces.ICompaniesDao;
import utils.JdbcUtils;

public class WorkSheet {

	public static void main(String[] args) {

		try {

			ICompaniesDao dao = new CompaniesDao();

//			Company company1;
//			Company company2;

//			company1 = new Company("Aa13467912", "Dor9711@gmail.com", "Dor");
//			dao.addCompany(company1);
//			company2 = new Company("Aa13467912", "Nil9711@gmail.com", "Ben");
//			company2.setId(1);
			
			
//			dao.deleteCompany(1);
//			System.out.println(dao.getCompanyIDByEmailAndPassword("Nil9711@gmail.com", "Aa13467912"));
//			System.out.println(dao.isCompanyGotCoupons(1));
//			dao.updateCompany(company2);
//			dao.addCompany(company1);

			System.out.println(dao.getAllCompanies());

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