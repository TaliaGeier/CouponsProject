package com.talia.coupons.interfaces;

import java.util.List;

import com.talia.coupons.beans.User;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.exceptions.ApplicationException;

public interface IUsersDao {
	long addUser(User user) throws ApplicationException;

	void updateUser(User user) throws ApplicationException;

	void deleteUser(long userID) throws ApplicationException;

	User getOneUser(long userID) throws ApplicationException;

	List<User> getAllUsers() throws ApplicationException;

	ClientType login(String userEmail, String password) throws ApplicationException;

	void deleteUsersByCompanyId(long companyId) throws ApplicationException;

	List<User> getCompanyUsers(long companyId) throws ApplicationException;

	boolean isUserExistsById(long userId) throws ApplicationException;

	boolean isUserExistsByEmail(String userEmail) throws ApplicationException;

}
