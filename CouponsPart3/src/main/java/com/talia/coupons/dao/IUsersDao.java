package com.talia.coupons.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talia.coupons.entities.User;
import com.talia.coupons.enums.ClientType;


@Repository

public interface IUsersDao extends CrudRepository<User, Long> {

	@Query(value = "DELETE FROM users WHERE company_id = :companyId", nativeQuery = true)
	public void deleteUsersByCompanyId(@Param("companyId") long companyId);

	public User findByUserName(String userName);

	public User findByUserNameAndUserPassword(String userName, String userPassword);
	
}