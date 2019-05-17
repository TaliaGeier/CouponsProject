package com.talia.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.talia.coupons.beans.Purchases;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

import com.talia.coupons.interfaces.IPurchasesDao;
import com.talia.coupons.utils.JdbcUtils;

@Repository
public class PurchasesDao implements IPurchasesDao{
	

	public long  addCouponPurchase(Purchases purchase) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO purchases (customer_id, coupon_id, purchase_amount) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, purchase.getCustomerId());
			preparedStatement.setLong(2, purchase.getCouponId());
			preparedStatement.setInt(3, purchase.getAmount());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();

			if (result.next()) {
				long id = result.getLong(1);
				purchase.setPurchaseId(id);
				return id;
			}
			
			throw new ApplicationException(ErrorType.INSERTION_ERROR, "Failed to add purchase");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.INSERTION_ERROR, "Failed to add purchase");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public void updatePurchase(Purchases purchase) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE purchases SET customer_id=?, coupon_id=?, purchase_amount = ? WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchase.getCustomerId());
			preparedStatement.setLong(2, purchase.getCouponId());
			preparedStatement.setInt(3, purchase.getAmount());
			preparedStatement.setLong(4, purchase.getPurchaseId());

			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.UPDATE_ERROR, "Failed to update purchase");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	
	

	public void deletePurchase(long purchaseID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE purchase_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchaseID);
			preparedStatement.executeUpdate();
			System.out.println("deleted");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete purchase");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	public void deleteExpiredPurchases() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id IN ( SELECT coupon_id FROM coupons WHERE coupon_end_date < curdate())";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete expired purchases");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	
	public void deletePurchasesByCustomerId(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR,
					"Failed to delete purchases by customer");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public void deletePurchasesByCouponId(long couponId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR,
					"Failed to delete purchases by coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void deletePurchasesByCompanyId(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM purchases WHERE coupon_id IN ( SELECT coupon_id FROM coupons WHERE company_id = ?)";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR,
					"Failed to delete purchases by company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	public Purchases getOnePurchase(long purchaseID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM Purchases WHERE purchase_id =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, purchaseID);
			result = preparedStatement.executeQuery();
			if (!result.next()) {
				return null;
			}
			return extractPurchaseFromResultSet(result);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get purchase");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	public List<Purchases> getAllPurchases() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Purchases purchase = null;
		List<Purchases> allPurchases = new ArrayList<Purchases>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM Purchases";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				purchase = extractPurchaseFromResultSet(result);
				allPurchases.add(purchase);
			}

			return allPurchases;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all purchases");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Purchases> getPurchasesByCouponId(long couponId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Purchases tempPurchase = new Purchases();
		List<Purchases> purchasesList = new ArrayList<Purchases>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM purchases WHERE coupon_id = ?");
			preparedStatement.setLong(1, couponId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				tempPurchase = extractPurchaseFromResultSet(result);
				purchasesList.add(tempPurchase);
			}

			return purchasesList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to get all purchases by coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Purchases> getPurchasesByCompanyId(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Purchases tempPurchase = new Purchases();
		List<Purchases> purchasesList = new ArrayList<Purchases>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM purchases WHERE coupon_id = coupon_id IN (SELECT `coupon_id` FROM `coupons` WHERE `company_id` = ?)");
			preparedStatement.setLong(1, companyId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				tempPurchase = extractPurchaseFromResultSet(result);
				purchasesList.add(tempPurchase);
			}

			return purchasesList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to get all purchases by company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public boolean isPurchaseExistsById(long purchaseId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM purchases WHERE purchase_id = ?");
			preparedStatement.setLong(1, purchaseId);
			result = preparedStatement.executeQuery();

			if (result.next()) {

				return true;

			}

			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.QUERY_ERROR,
					"Failed to establish whether purchase exists");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	

	private Purchases extractPurchaseFromResultSet(ResultSet result)
			throws ApplicationException, SQLException {
		Purchases purchase = new Purchases();
	
		purchase.setPurchaseId(result.getLong("purchase_id"));
		purchase.setCustomerId(result.getLong("customer_id"));
		purchase.setCouponId(result.getLong("coupon_id"));
		purchase.setAmount(result.getInt("purchase_amount"));
		return purchase;
	
	}

}
