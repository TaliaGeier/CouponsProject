package com.talia.coupons.main;

import beans.*;
import daily.job.CouponExpirationDailyJob;
import logic.*;

public class Test {

	public static void testAll() {

		LoginManager loginManager = LoginManager.getInstance();



		try {

// -----------------Objects Creation
			Coupon coup0 = new Coupon(1, Category.ELECTRONICS, "Iphone 9 coupon", "50% Discount", "2018-03-10",
					"2019-03-10", 3, 500.10, null);
			Coupon coup1 = new Coupon(1, Category.FURNITURE, "Table Coupon", "50% Discount", "2018-03-10", "2019-03-10",
					2, 100.50, null);
			Coupon coup2 = new Coupon(1, Category.MOVIES, "Guardians of the galaxy 2", "50% Discount", "2018-03-10",
					"2019-03-10", 2, 35.5, null);
	

			Company com0 = new Company("Aa13457896", "Nil9711@gmail.com", "NilCompany");
			Company com1 = new Company("Aa13457896", "Dor9711@gmail.com", "DorCompany");
			Company com2 = new Company("Aa13457896", "Talia9711@gmail.com", "TaliaCompany");
			Company com3 = new Company("Aa13457896", "Bob@gmail.com", "BobCompany");

			Customer cus0 = new Customer("Aa13457896", "Nil9711@gmail.com", "nil", "Golan");
			Customer cus1 = new Customer("Aa13457896", "Dor9711@gmail.com", "dor", "david");
			Customer cus2 = new Customer("Aa13457896", "Talia9711@gmail.com", "talia", "geier");
			Customer cus3 = new Customer("Aa13457896", "BobKid@gmail.com", "Bobi", "Alice");
			
// -----------------STARTING THE DAILY JOB 
				
			daily.job.CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
			Thread DailyJobThread = new Thread(dailyJob, "daily Job");
			DailyJobThread.start();	
			
// -----------------ADMIN

			System.out.println("----------------------Checking Admin");
			AdminController admin = (AdminController) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

			admin.addCompany(com0);
			admin.addCompany(com1);
			admin.addCompany(com2);
			admin.addCompany(com3);

			admin.deleteCompany(4);
			com2.setEmail("Taliah9711@icloud.com");
			admin.updateCompany(com2);

			admin.addCustomer(cus0);
			admin.addCustomer(cus1);
			admin.addCustomer(cus2);

			admin.deleteCustomer(4);
			cus2.setEmail("Taliah9711@icloud.com");
			admin.updateCustomer(cus2);

			System.out.println(admin.getAllCustomers());
			System.out.println(admin.getOneCustomer(2));
			System.out.println(admin.getOneCompany(2));
			System.out.println(admin.getAllCompanies());

// -----------------COMPANY
			System.out.println("----------------------Checking Company");

			CompanyController company = (CompanyController) loginManager.login("Nil9711@gmail.com", "Aa13457896",
					ClientType.COMPANY);

			company.addCoupon(coup0);
			company.addCoupon(coup1);
			company.addCoupon(coup2);

			coup1.setAmount(5);
			company.updateCoupon(coup1);

			System.out.println(company.getCompanyCoupons());
			System.out.println(company.getCompanyCoupons(Category.FURNITURE));
			System.out.println(company.getCompanyCoupons(600.50));
			System.out.println(company.getCompanyDetails());

			company.deleteCoupon(3);

			// -----------------CUSTOMER
			System.out.println("----------------------Checking Customer");

			CustomerController customer = (CustomerController) loginManager.login("Nil9711@gmail.com", "Aa13457896",
					ClientType.CUSTOMER);

			customer.purchaseCoupon(coup0);
			customer.purchaseCoupon(coup1);

			System.out.println(customer.getCustomerCoupons());
			System.out.println(customer.getCustomerCoupons(Category.FURNITURE));
			System.out.println(customer.getCustomerCoupons(300.0));
			System.out.println(customer.getCustomerDetails());

// -----------------STOPPING THE DAILY JOB 
			DailyJobThread.stop();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		

	}

}
