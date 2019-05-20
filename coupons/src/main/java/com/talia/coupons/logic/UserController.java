package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.User;
import com.talia.coupons.beans.UserDataMap;
import com.talia.coupons.beans.UserLoginDetails;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.interfaces.ICacheManager;
import com.talia.coupons.utils.REGEX;

@Controller
public class UserController {
	
	@Autowired
	private UsersDao userDao;
	@Autowired
	private CompanyController companyController;
	
	@Autowired
	private CacheManager cacheManager;
	
	public UserDataMap login(String email, String password) throws ApplicationException {
		ClientType clientType = userDao.login(email, password);
		
		if (clientType == null)
		{
			throw new ApplicationException(ErrorType.LOGIN_FAILED, "Invalid user or password");
		}
		
		int token = generateEncryptedToken(email);
		UserDataMap userDataMap = generateLoginDetails(email);
		cacheManager.put(token, userDataMap);
		
		return userDataMap;
	}

	private UserDataMap generateLoginDetails(String email) throws ApplicationException {
		User user = userDao.getOneUserByEmail(email);
		UserDataMap userDataMap = new UserDataMap(
				user.getUserId(),
				user.getCompanyId(),
				user.getUserLoginDetails().getType());
				
		
		return userDataMap;
	}

	private int generateEncryptedToken(String user) {
		String token = "Salt - junk data" + user + "Sheker kolshehu";
		return token.hashCode();
	}
	
	public long addUser(User user) throws ApplicationException {

		isUserValidToAdd(user);
		return userDao.addUser(user);
	}
	public User getOneUser(long userId) throws ApplicationException {
		if(userDao.isUserExistsById(userId)) {
			return userDao.getOneUser(userId);
		}
		throw new ApplicationException(ErrorType.READ_ERROR, "Failed to get user");
	}

	public List<User> getAllUsers() throws ApplicationException {

		return userDao.getAllUsers();
	}

	public void updateUser(User user) throws ApplicationException {
		isUserValidToUpdate(user);
		userDao.updateUser(user);
	}

	public void deleteUser(long userToDelete) throws ApplicationException {
		if(userDao.isUserExistsById(userToDelete)) {
			deleteUserLogic(userToDelete);
			userDao.deleteUser(userToDelete);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete user");
	}
	
	public void deleteUsersByCompanyId(long companyId) throws ApplicationException {
		if (companyController.isCompanyExists(companyId)) {
			userDao.deleteUsersByCompanyId(companyId);
		}
		throw new ApplicationException(ErrorType.DELETE_ERROR, "Failed to delete users");
	}
	
	
	private void isUserValidToAdd(User user) throws ApplicationException {

		if (user.getUserLoginDetails().getUserEmail() == null) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "invalid data entered.");
		}

		if (userDao.isUserExistsByEmail(user.getUserLoginDetails().getUserEmail())) {
			throw new ApplicationException(ErrorType.DUPLICATED_ENTRY, "User email is Taken");
		}

		if (!REGEX.isEmailValid(user.getUserLoginDetails().getUserEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "User Email invalid");
		}

		if (user.getUserLoginDetails().getPassword() == null
				|| user.getUserLoginDetails().getPassword().length() <= 6) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "user password must contain atleast 6 chars.");
		}



	}
	private void isUserValidToUpdate(User user) throws ApplicationException {

		if (!REGEX.isEmailValid(user.getUserLoginDetails().getUserEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "User Email invalid");
		}

		if (userDao.getOneUser(user.getUserId()).getCompanyId() != user.getCompanyId()) {
			throw new ApplicationException(ErrorType.UPDATE_ERROR, "Cant update user company Id.");
		}



	}
	private void deleteUserLogic(long userIdToDelete) throws ApplicationException {

		CustomerController customerController = new CustomerController();
		User userToDelete = userDao.getOneUser(userIdToDelete);

		if (userToDelete == null) {
			throw new ApplicationException(ErrorType.READ_ERROR, "user: " + userIdToDelete + " was not found");
		}
		/*
		 * if the user is a customer we must delete the customer info before deleting
		 * the user
		 */
		
		if (userToDelete.getUserLoginDetails().getType() == ClientType.CUSTOMER) {
			customerController.deleteCustomer(userIdToDelete);
			return;
		}

		/*
		 * if the user is a company representative
		 */
		if (userToDelete.getUserLoginDetails().getType() == ClientType.COMPANY) {
			userDao.deleteUser(userIdToDelete);
			return;
		}

		/*
		 * if the user is an administrator.
		 */
		if (userToDelete.getUserLoginDetails().getType() == ClientType.ADMINISTRATOR) {
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "Cant delete administrators");
		}

	}
}
