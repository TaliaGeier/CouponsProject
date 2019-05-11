package com.talia.coupons.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.talia.coupons.dao.CompaniesDao;
import com.talia.coupons.dao.CouponsDao;
import com.talia.coupons.dao.CustomersDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.Company;
import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Customer;
import com.talia.coupons.beans.Purchases;
import com.talia.coupons.beans.User;
import com.talia.coupons.beans.UserLoginDetails;
import com.talia.coupons.utils.DateUtils;
import com.talia.coupons.enums.Category;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.logic.CompanyController;

public class TestingDao {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		CompaniesDao companyDao = new CompaniesDao();
		CouponsDao couponsDao = new CouponsDao();
		PurchasesDao purchasesDao = new PurchasesDao();
		CustomersDao customersDao = new CustomersDao();
		UsersDao usersDao = new UsersDao();
//		CompanyController companyController = new CompanyController(companyDao);
		
		try {
			
			// ------- CompanyController ------
			Company company1 = new Company(2, "He@gmail12345.com", "abcklk");
//			companyController.addCompany(company1);
//			System.out.println(companyController.getAllCompanies());
//			companyController.updateCompany(company1);
//			companyController.addCompany(company1);
//		System.out.println(	companyController.getOneCompany(1));
//			companyController.updateCompany(company1);
			
//			companyController.deleteCompany(2);
			// ------ COMPANY DAO ---------
			
//			Company company1 = new Company("Hermes23", "Birkin3132");
//			Company company2 = new Company(2, "ace@gmail.com", "ace");
//			companyDao.isCompanyExistsByEmail("dell@gmail.com");
//			companyDao.isCompanyExistsById(1);
//			companyDao.isCompanyExistsByName("dell");
//			companyDao.isCompanyGotCoupons(1);
			
//			System.out.println(companyDao.addCompany(company1));
//			couponsDao.deleteAllExpiredCoupons();
//			System.out.println(companyDao.getOneCompany(2));
//			companyDao.updateCompany(company2);
//			companyDao.deleteCompany(3);
//			System.out.println(companyDao.getAllCompanies());
//			System.out.println(companyDao.getOneCompany(1));
//			System.out.println(companyDao.getCompanyCoupons(2));
//			System.out.println(companyDao.getCompanyCoupons(1, Category.FOOTBALL_GAME));
//			System.out.println(companyDao.getCompanyCoupons(2, 70.5));
//			System.out.println(companyDao.getCompanyDetails(1));
//			System.out.println(companyDao.getCompaniesRegisteredNum());
			
			
			// ------ COUPON DAO ---------
//			System.out.println(couponsDao.getAllCouponsByCategory(Category.FURNITURE));
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			
//			couponsDao.deleteAllExpiredCoupons();
//			System.out.println(CouponsDao.getCategoryID(Category.FOOTBALL_GAME));
//			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-24");
//			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-29");
//			Coupon coupon1 = new Coupon(1, Category.FURNITURE, "fun" , "yay", "2019-05-24", "2019-06-29", 30, 95.5, "fkjgot");
//			System.out.println(couponsDao.addCoupon(coupon1));

//			Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-30");
//			Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-31");
//			Coupon coupon2 = new Coupon(1, 2, Category.PUBLIC_TRANSPORTATION, "travel anywhere", "save so much coins", date3, date4, 80, 100.0, null);
//			couponsDao.updateCoupon(coupon2);
//			couponsDao.deleteCoupon(2);
//			couponsDao.deleteAllCompanyCoupons(2);
//			System.out.println(couponsDao.getAllCoupons());
//			System.out.println(couponsDao.getOneCoupon(8));
//			couponsDao.isTitleExists(1, "yay123");
//			couponsDao.isCouponExpired(coupon1);
//			couponsDao.reduceCouponStock(10);
//			System.out.println(couponsDao.getAllExpiredCouponsById());
//			System.out.println(couponsDao.getCategoryID(Category.MOVIES));
			
			// ------ CUSTOMERS DAO ---------
//			UserLoginDetails userlog1 = new UserLoginDetails("man@gmail.com", "1234", ClientType.COMPANY);
//			User user1 = new User(4, 1);
//			Customer customer1 = new Customer("George", "Smith", user1);
//			customersDao.addCustomer(customer1);
			
//			System.out.println(customersDao.getOneCustomer(3));
			
//			customersDao.isCustomerGotCoupons(2);
//			Customer customer2 = new Customer(2, "serena", "vanderwoodsen");
//			customersDao.updateCustomer(customer2);
//			customersDao.deleteCustomer(2);
//			System.out.println(customersDao.getOneCustomer(3));
//			System.out.println(usersDao.getAllUsers());
//			System.out.println(customersDao.getAllCustomers());
//			System.out.println(customersDao.getOneCustomer(6));
//			System.out.println(customersDao.getCustomerCoupons(3));
//			System.out.println(customersDao.getCustomerCoupons(3, Category.ELECTRONICS));
//			System.out.println(customersDao.getCustomerCoupons(3, 50.0));
//			System.out.println(customersDao.getCustomerDetails(3));
			
			// ------ USERS DAO ---------

//			UserLoginDetails userlog1 = new UserLoginDetails("man@gmail.com", "1234", ClientType.COMPANY);
////			User user1 = new User(1, userlog1);
//			User user1 = new User((long) 1, userlog1);
//		System.out.println(usersDao.addUser(user1));
		

//			System.out.println(usersDao.isUserExistsByEmail("man@gmail.com"));
//			System.out.println(usersDao.isUserExistsById(2));
//			usersDao.isCustomerExists("men@gmail.com", "1234");
//			usersDao.isCustomerExistsByEmail("men@gmail.com");
//			System.out.println(usersDao.getCustomerIDByEmailAndPassword("man@gmail.com5", "1234"));
//			UserLoginDetails userlog2 = new UserLoginDetails("talia@gmail.com", "1357", ClientType.ADMINISTRATOR);
//			User user2 = new User(1, (long) 1, userlog2);
//			usersDao.updateUser(user2);
//			System.out.println(usersDao.isUserExistsById(8));
//			System.out.println(usersDao.getCompanyUsers(1));
//			usersDao.deleteUsersByCompanyId(1);
//			usersDao.deleteUser(2);
//			System.out.println(usersDao.getOneUser(1));
//			System.out.println(usersDao.getAllUsers());
			
			// ------ PURCHASES DAO ---------
			
//			if(!purchasesDao.isCouponPurchased(3, 9));
//			purchasesDao.deleteOneCouponPurchase(3, 1);
//			Purchases purchase = new Purchases(4, 1, 52);
//			purchasesDao.addCouponPurchase(purchase);
//			Purchases purchase = new Purchases(3, 3, 9, 36);
//			purchasesDao.updatePurchase(purchase);
//			purchasesDao.deletePurchase(3);
//			System.out.println(purchasesDao.getOnePurchase(2));
//			System.out.println(purchasesDao.getAllPurchases());
			
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		}
		
	}


