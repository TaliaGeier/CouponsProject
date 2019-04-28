package com.talia.coupons.logic;

import java.util.List;

import com.talia.coupons.dao.UsersDao;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.User;
import com.talia.coupons.beans.UserLoginDetails;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.utils.REGEX;

public class UserController {
	private UsersDao userDao;
	
	public ClientType login(UserLoginDetails userLoginDetails) throws ApplicationException {
		return userDao.login(userLoginDetails.getUserEmail(), userLoginDetails.getPassword());
	}
	
	public long addUser(User user) throws ApplicationException {

		isUserValidToAdd(user);
		return userDao.addUser(user);
	}
	public User getOneUser(long userId) throws ApplicationException {

		return userDao.getOneUser(userId);
	}

	public List<User> getAllUsers() throws ApplicationException {

		return userDao.getAllUsers();
	}

	public void updateUser(User user) throws ApplicationException {
		isUserValidToUpdate(user);
		userDao.updateUser(user);
	}

	public void deleteUser(long userToDelete) throws ApplicationException {
		deleteUserLogic(userToDelete);
		userDao.deleteUser(userToDelete);
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

		if (user.getCompanyId() == null || user.getCompanyId() == 0) {
			user.getUserLoginDetails().setType(ClientType.CUSTOMER);
		} else {
			user.getUserLoginDetails().setType(ClientType.COMPANY);
		}

	}
	private void isUserValidToUpdate(User user) throws ApplicationException {

		if (!REGEX.isEmailValid(user.getUserLoginDetails().getUserEmail())) {
			throw new ApplicationException(ErrorType.INVALID_INPUT, "User Email invalid");
		}

		if (userDao.getOneUser(user.getUserId()).getCompanyId() != user.getCompanyId()) {
			throw new ApplicationException(ErrorType.UPDATE_ERROR, "Cant update user company Id.");
		}

		if (userDao.getOneUser(user.getUserId()).getUserLoginDetails().getType() != user.getUserLoginDetails()
				.getType()) {
			throw new ApplicationException(ErrorType.UPDATE_ERROR, "Cant update user Type.");
		}

	}
	private void deleteUserLogic(long userIdToDelete) throws ApplicationException {

		CustomerController customerController = new CustomerController();
		User userToDelete = userDao.getOneUser(userIdToDelete);

		if (userToDelete == null) {
			throw new ApplicationException(ErrorType.READ_ERROR, "user: " + userIdToDelete + " was not found");
		}
		/*
		 * if the user is a customer we must delete his customer table before deleting
		 * the user. so we call the customer controller to delete the customer and his
		 * purchases.
		 */
		if (userToDelete.getUserLoginDetails().getType() == ClientType.CUSTOMER) {
			customerController.deleteCustomer(userIdToDelete);
			return;
		}

		/*
		 * if the user is a company agent
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
