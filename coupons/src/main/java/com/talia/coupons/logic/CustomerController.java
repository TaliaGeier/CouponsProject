package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.CustomersDao;
import com.talia.coupons.dao.PurchasesDao;
import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Customer;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomersDao customerDao;
	
	@Autowired
	private PurchaseController purchaseController;
	
	@Autowired
	private UserController userController;

	public long addCustomer(Customer customer) throws ApplicationException {


		isCustomerValid(customer);
		long id = userController.addUser(customer.getUser());
		customer.setUserId(id);

		return customerDao.addCustomer(customer);

	}

	public Customer getOneCustomer(long customerId) throws ApplicationException {
		
		if(isCustomerExists(customerId)) {

		return customerDao.getOneCustomer(customerId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get customer");
	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		return customerDao.getAllCustomers();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		isCustomerValid(customer);
		customerDao.updateCustomer(customer);

	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		if(isCustomerExists(customerId)) {
		
		purchaseController.deletePurchasesByCustomerId(customerId);
		customerDao.deleteCustomer(customerId);
		userController.deleteUser(customerId);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete customer");
	}
	
	public boolean isCustomerExists (long customerID)throws ApplicationException {
		if(customerDao.isCustomerExistsById(customerID)) {
			return true;
		}
		return false;
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
