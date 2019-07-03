package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.ICustomersDao;
import com.talia.coupons.entities.Customer;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class CustomerController {
	
	@Autowired
	private ICustomersDao customerDao;
	
	

	public long addCustomer(Customer customer) throws ApplicationException {


		isCustomerValid(customer);
		customerDao.save(customer);
		return customer.getCustomerId();

	}

	public Customer getOneCustomer(long customerId) throws ApplicationException {
		
		return customerDao.findById(customerId).get();

	}

	public List<Customer> getAllCustomers() throws ApplicationException {

		return (List<Customer>) customerDao.findAll();
	}

	public void updateCustomer(Customer customer) throws ApplicationException {

		isCustomerValid(customer);
		customerDao.save(customer);

	}

	public void deleteCustomer(long customerId) throws ApplicationException {
		customerDao.deleteById(customerId);

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
