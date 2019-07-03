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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talia.coupons.beans.User;
import com.talia.coupons.beans.UserDataMap;
import com.talia.coupons.beans.UserLoginDetails;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.logic.UserController;

@RestController
@RequestMapping("/users")
public class UserApi {

	@Autowired
	private UserController userController;

	@PostMapping("/login")
	public UserDataMap login(@RequestBody User user) throws ApplicationException {
		return userController.login(user.getUserLoginDetails().getUserEmail(), user.getUserLoginDetails().getPassword());
	}

	@PostMapping
	public void addUser(@RequestBody User user) throws ApplicationException {
		userController.addUser(user);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") long userId) throws ApplicationException {
		return userController.getOneUser(userId);
	}

	@GetMapping
	public List<User> getAllUsers() throws ApplicationException {
		return userController.getAllUsers();
	}

	@PutMapping
	public void updateUser(@RequestBody User user) throws ApplicationException {
		userController.updateUser(user);
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long userId) throws ApplicationException {
		userController.deleteUser(userId);
	}

}
