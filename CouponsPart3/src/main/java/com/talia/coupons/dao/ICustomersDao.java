package com.talia.coupons.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.talia.coupons.entities.Customer;


@Repository

public interface ICustomersDao extends CrudRepository<Customer, Long> {
	
}
