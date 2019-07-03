package com.talia.coupons.interfaces;

import java.util.List;

import com.talia.coupons.beans.Customer;
import com.talia.coupons.exceptions.ApplicationException;


public interface ICustomersDao {


	long addCustomer(Customer customer) throws ApplicationException;

	void updateCustomer(Customer customer) throws ApplicationException;

	void deleteCustomer(long customerID) throws ApplicationException;

	List<Customer> getAllCustomers() throws ApplicationException;

	Customer getOneCustomer(long customerID) throws ApplicationException;



}
