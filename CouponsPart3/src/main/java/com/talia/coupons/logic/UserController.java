package com.talia.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.talia.coupons.dao.ICustomersDao;
import com.talia.coupons.dao.IUsersDao;
import com.talia.coupons.entities.User;
import com.talia.coupons.beans.UserData;
import com.talia.coupons.beans.UserDataMap;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

@Controller
public class UserController {
	
	@Autowired
	private IUsersDao userDao;
	@Autowired
	private ICustomersDao customerDao;

	@Autowired
	private ICacheManager cacheManager;
	
	public UserDataMap login(User user) throws ApplicationException {

		UserDataMap tokenTypeAndId = new UserDataMap();
		UserData userData = new UserData();
		User userLoggingIn = userDao.findByUserNameAndUserPassword(user.getUserName(), user.getUserPassword());

		if (userLoggingIn == null) {
			throw new ApplicationException(ErrorType.LOGIN_FAILED, "Failed To Log In");
		}

		User userLoginOk = userDao.findByUserName(user.getUserName());

		int token = generateEncryptedToken(userLoginOk.getUserName());

		if (userLoginOk.getCompany() != null) {
			userData = new UserData(userLoginOk.getUserId(), userLoginOk.getCompany().getId(),
					userLoginOk.getUserType());
			tokenTypeAndId = new UserDataMap(userLoginOk.getUserId(), userLoginOk.getCompany().getId(),  userLoginOk.getUserType(), token);
		}
		userData = new UserData(userLoginOk.getUserId(), null, userLoginOk.getUserType());
		tokenTypeAndId = new UserDataMap(userLoginOk.getUserId(), null,  userLoginOk.getUserType(), token);

		cacheManager.put(token, userData);

		return tokenTypeAndId;

	}

	private int generateEncryptedToken(String user) {
		String token = "Salt - junk data" + user + "Sheker kolshehu";
		return token.hashCode();
	}
	
	public long addUser(User user) throws ApplicationException {

		validateUser(user);
		userDao.save(user);
		return user.getUserId();
	}
	public User getOneUser(long userId) throws ApplicationException {
		return userDao.findById(userId).get();
	}

	public List<User> getAllUsers() throws ApplicationException {

		return (List<User>) userDao.findAll();
	}

	public void updateUser(User user) throws ApplicationException {
		userDao.save(user);
	}

	@Transactional
	public void deleteUser(long userId) {
		if (customerDao.findById(userId).get() != null) {
			customerDao.deleteById(userId);
			return;
		}
		userDao.deleteById(userId);
	}
	

	
	
	private void validateUser(User user) throws ApplicationException {

		User userCheck = userDao.findByUserName(user.getUserName());

		if (userCheck != null) {
			throw new ApplicationException(ErrorType.INSERTION_ERROR, "User Name is taken.");
		}


	}

}
