package com.talia.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talia.coupons.beans.Customer;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

import com.talia.coupons.interfaces.ICustomersDao;
import com.talia.coupons.utils.JdbcUtils;


public class CustomersDao implements ICustomersDao {

	public long addCustomer(Customer customer) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO customers (customer_id, customer_last_name ,customer_first_name) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customer.getUser().getUserId());
			preparedStatement.setString(2, customer.getCustomerLastName());
			preparedStatement.setString(3, customer.getCustomerFirstName());
			

			preparedStatement.executeUpdate();
			
//			if (getOneCustomer(customer.getUser().getUserId()) != null) {
//			
//			}
			return customer.getUser().getUserId();
			
//			throw new ApplicationException(ErrorType.INSERTION_ERROR, "Failed to create new customer");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.INSERTION_ERROR, "Failed to create new customer");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}



	public void updateCustomer(Customer customer) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE CUSTOMERS SET customer_first_name =?, customer_last_name=? WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, customer.getCustomerFirstName());
			preparedStatement.setString(2, customer.getCustomerLastName());
			preparedStatement.setLong(3, customer.getUser().getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.UPDATE_ERROR, "Failed to update customer");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	
	public void deleteCustomer(long customerID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM CUSTOMERS WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete customer");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}


	public List<Customer> getAllCustomers() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Customer customer = null;
		List<Customer> allCustomers = new ArrayList<Customer>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM customers";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				customer = extractCustomerFromResultSet(result);
				allCustomers.add(customer);
			}

			return allCustomers;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all customers");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	
	public Customer getOneCustomer(long customerID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM CUSTOMERS WHERE customer_id =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerID);
			result = preparedStatement.executeQuery();
			if (!result.next()) {
				return null;
			}
			return extractCustomerFromResultSet(result);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get customer");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}


	public boolean isCustomerExistsById(long customerID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");

			preparedStatement.setLong(1, customerID);
			result = preparedStatement.executeQuery();

			if (result.next()) {

				return true;

			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to check if customer exists with id " + customerID);
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	private Customer extractCustomerFromResultSet(ResultSet result)
			throws ApplicationException, SQLException {
	
		Customer customer = new Customer();
		customer.setUserId(result.getLong("customer_id"));
		customer.setCustomerFirstName(result.getString("customer_first_name"));
		customer.setCustomerLastName(result.getString("customer_last_name"));

		return customer;
	
	}

}