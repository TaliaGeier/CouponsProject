package com.talia.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talia.coupons.beans.User;
import com.talia.coupons.beans.UserLoginDetails;
import com.talia.coupons.exceptions.ApplicationException;

import com.talia.coupons.interfaces.IUsersDao;
import com.talia.coupons.enums.ClientType;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.utils.DateUtils;
import com.talia.coupons.utils.JdbcUtils;

public class UsersDao implements IUsersDao{
	
	public long addUser(User user) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO users (user_name, user_password,user_type, company_id) VALUES (?,?,?,?)";
			
			preparedStatement = connection.prepareStatement(sqlStatement, preparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, user.getUserLoginDetails().getUserEmail());
			preparedStatement.setString(2, user.getUserLoginDetails().getPassword());
			preparedStatement.setString(3, user.getUserLoginDetails().getType().name());

			if (user.getCompanyId() == null) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}

			preparedStatement.executeUpdate();

			result = preparedStatement.getGeneratedKeys();

			if (result.next()) {
				long id = result.getLong(1);
				user.setUserId(id);
				return id;
			}

			throw new ApplicationException(ErrorType.INSERTION_ERROR, "Failed to add user");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.INSERTION_ERROR, "Failed to add user");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE users SET user_name=? , user_password=? , user_type=? , company_id=? WHERE user_id=?";

			preparedStatement = connection.prepareStatement(sqlStatement);

			preparedStatement.setString(1, user.getUserLoginDetails().getUserEmail());
			preparedStatement.setString(2, user.getUserLoginDetails().getPassword());
			preparedStatement.setString(3, user.getUserLoginDetails().getType().name());

			if (user.getCompanyId() == null) {
				preparedStatement.setString(4, null);
			} else {
				preparedStatement.setLong(4, user.getCompanyId());
			}

			preparedStatement.setLong(5, user.getUserId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.UPDATE_ERROR,
					"Failed to update user");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	public void deleteUser(long userID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM USERS WHERE user_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete user");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	
	public User getOneUser(long userID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM USERS WHERE user_id =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userID);
			result = preparedStatement.executeQuery();
			if (!result.next()) {
				return null;
			}
			return extractUserFromResultSet(result);
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get user");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<User> getAllUsers() throws ApplicationException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = new User();
		List<User> allUsers = new ArrayList<User>();
	
		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM USERS";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				user = extractUserFromResultSet(result);
				allUsers.add(user);
			}
	
			return allUsers;
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get users");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public ClientType login(String userEmail, String password) throws ApplicationException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet result=null;

		try {
			connection=JdbcUtils.getConnection();

			String sqlStatement="SELECT * FROM Users WHERE user_name = ? && user_password = ?";

			preparedStatement=connection.prepareStatement(sqlStatement);

			preparedStatement.setString(1, userEmail);
			preparedStatement.setString(2, password);

			result=preparedStatement.executeQuery();

			if (result.next()) {
				ClientType clientType = ClientType.valueOf(result.getString("type"));
				return clientType;
			}

			return null;
			
		} catch (SQLException e) {
			throw new ApplicationException( e, ErrorType.LOGIN_FAILED, "User login failed");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);	
		}
	}
	
	
	
	public void deleteUsersByCompanyId(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM users WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR,
					"Delete users by company Id : " + companyId + " failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	
	public List<User> getCompanyUsers(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		User user = new User();
		List<User> usersList = new ArrayList<User>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE company_id = ?");
			preparedStatement.setLong(1, companyId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				user = extractUserFromResultSet(result);
				usersList.add(user);
			}

			return usersList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to get company users by company id : " + companyId);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public boolean isUserExistsById(long userId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");

			preparedStatement.setLong(1, userId);
			result = preparedStatement.executeQuery();

			if (result.next()) {

				return true;

			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to check if user exists with id " + userId);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public boolean isUserExistsByEmail(String userEmail) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_name = ?");
			preparedStatement.setString(1, userEmail);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"failed to check if a user exist by email : " + userEmail);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}


	

	

	private User extractUserFromResultSet(ResultSet result)
			throws ApplicationException, SQLException {
		User user = new User();
		UserLoginDetails userLoginDetails = new UserLoginDetails();
		userLoginDetails.setUserEmail(result.getString("user_name"));
		userLoginDetails.setPassword(result.getString("user_password"));
		userLoginDetails.setType(ClientType.valueOf(result.getString("user_type")));
		user.setUserId(result.getLong("user_id"));
		user.setCompanyId(result.getLong("company_id"));
		user.setUserLoginDetails(userLoginDetails);
		return user;
	}

}
