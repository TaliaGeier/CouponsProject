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

import com.talia.coupons.entities.Customer;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.logic.CustomerController;

@RestController
@RequestMapping("/customers")

public class CustomerApi {

	@Autowired
	private CustomerController customerController;
	
	@PostMapping
	public long addCustomer(@RequestBody Customer customer) throws ApplicationException {
		return customerController.addCustomer(customer);
	}

	@GetMapping("/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		return customerController.getOneCustomer(customerId);
	}

	@GetMapping
	public List<Customer> getAllCustomers() throws ApplicationException {
		return customerController.getAllCustomers();
	}

	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		customerController.updateCustomer(customer);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		customerController.deleteCustomer(customerId);
	}
}

