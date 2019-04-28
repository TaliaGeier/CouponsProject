package com.talia.coupons.logic;

import java.util.List;

import com.talia.coupons.dao.CustomersDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Customer;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

public class CustomerController {
	
	private CustomersDao customerDao;
	private UsersDao userDao;
	private PurchasesDao purchaseDao;

	public long addCustomer(Customer customer) throws ApplicationException {

		UserController userController = new UserController();

		isCustomerValid(customer);

		long id = userController.addUser(customer.getUser());
		customer.setCustomerId(id);

		return customerDao.addCustomer(customer);

	}

	public Customer getOneCustomer(long customerId) throws ApplicationException {

		return customerDao.getOneCustomer(customerId);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		return customerDao.getAllCustomers();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		isCustomerValid(customer);
		customerDao.updateCustomer(customer);

	}

	public void deleteCustomer(long customerId) throws ApplicationException {

		purchaseDao.deletePurchasesByCustomerId(customerId);
		customerDao.deleteCustomer(customerId);
		userDao.deleteUser(customerId);

	}

	private void isCustomerValid(Customer customer) throws ApplicationException {
		if (customer.getCustomerFirstName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "First Name too short");
		}
		if (customer.getCustomerLastName().length() < 2) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "Last Name too short");

		}
	}
}
