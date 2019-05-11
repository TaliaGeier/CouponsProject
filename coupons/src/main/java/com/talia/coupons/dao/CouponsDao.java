package com.talia.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.enums.Category;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

import com.talia.coupons.interfaces.ICouponsDao;
import com.talia.coupons.utils.JdbcUtils;

public class CouponsDao implements ICouponsDao {

	public long addCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO COUPONS (company_id, category_id, coupon_title, coupon_description, coupon_start_date, coupon_end_date, coupon_amount, coupon_price, coupon_image) VALUES (?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setLong(2, getCategoryID(coupon.getCategory()));
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());

			preparedStatement.setDate(5, new java.sql.Date(coupon.getStart_date().getTime()));
			preparedStatement.setDate(6, new java.sql.Date(coupon.getEnd_date().getTime()));
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImg());

			preparedStatement.executeUpdate();

			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				long id = result.getLong(1);
				coupon.setCouponId(id);
				return id;
			}
			throw new ApplicationException(ErrorType.INSERTION_ERROR, "Failed to Add coupon");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.INSERTION_ERROR, "Failed to Add coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlStatement = "UPDATE COUPONS SET company_id=?, category_id=?, coupon_title=?, coupon_description=?, coupon_start_date=?, coupon_end_date=?, coupon_amount=?, coupon_price=?, coupon_image=?  WHERE coupon_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setLong(2, getCategoryID(coupon.getCategory()));
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());

			preparedStatement.setDate(5, new java.sql.Date(coupon.getStart_date().getTime()));
			preparedStatement.setDate(6, new java.sql.Date(coupon.getEnd_date().getTime()));
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImg());
			preparedStatement.setLong(10, coupon.getCouponId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.UPDATE_ERROR, "Failed to update coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCoupon(long couponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM COUPONS WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteAllCompanyCoupons(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM COUPONS WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete all company's coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteAllExpiredCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Date currentDate = new Date();

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT coupon_id , coupon_end_date FROM coupons";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				if (currentDate.after(result.getDate("coupon_end_date")))
					deleteCoupon(result.getLong("coupon_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete all expired coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = new Coupon();
		List<Coupon> allCoupons = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM COUPONS";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				coupon = extractCouponFromResultSet(result);
				allCoupons.add(coupon);
			}

			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public Coupon getOneCoupon(long couponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM COUPONS WHERE coupon_id = ?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, couponID);

			result = preparedStatement.executeQuery();
			if (result.next()) {
				return extractCouponFromResultSet(result);
			}

			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get coupon");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public List<Coupon> getAllCouponsByMaxPrice(Double price) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = new Coupon();
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM COUPONS WHERE coupon_price < ? ");
			preparedStatement.setDouble(1, price);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				coupon = extractCouponFromResultSet(result);
				couponsList.add(coupon);
			}

			return couponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all coupons by max price.");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = new Coupon();
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM COUPONS WHERE category_id = ? ");
			preparedStatement.setLong(1, getCategoryID(category));
			result = preparedStatement.executeQuery();

			while (result.next()) {
				coupon = extractCouponFromResultSet(result);
				couponsList.add(coupon);
			}

			return couponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all coupons by category");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Coupon> getCompanyCoupons(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Coupon coupon = new Coupon();
		List<Coupon> companyCouponsList = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM COUPONS where company_id = ?");
			preparedStatement.setLong(1, companyId);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				coupon = extractCouponFromResultSet(result);
				companyCouponsList.add(coupon);
			}

			return companyCouponsList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get company coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}

	}

	public List<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> customerCoupons = new ArrayList<Coupon>();
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection
					.prepareStatement("SELECT * FROM coupons WHERE company_id=? AND category_id=?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.setLong(2, getCategoryID(category));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = extractCouponFromResultSet(resultSet);
				customerCoupons.add(coupon);
			}

			return customerCoupons;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR,
					"Failed to get all company : " + companyId + " coupons by category : " + category.name());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(long companyId, double maxPrice) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> customerCoupons = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection
					.prepareStatement("SELECT * FROM coupons WHERE company_id=? AND coupon_price <=?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.setDouble(2, maxPrice);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = extractCouponFromResultSet(resultSet);
				customerCoupons.add(coupon);
			}
			return customerCoupons;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get company coupons by max price");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCustomerCoupons(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> customerCoupons = new ArrayList<Coupon>();

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "SELECT * FROM coupons WHERE coupon_id IN (SELECT coupon_id FROM purchases WHERE customer_id=?)";

			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = extractCouponFromResultSet(resultSet);
				customerCoupons.add(coupon);
			}
			return customerCoupons;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get customer coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> filteredCustomerCoupons = new ArrayList<Coupon>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "SELECT * FROM coupons WHERE coupon_id IN (SELECT coupon_id FROM purchases WHERE customer_id=?) AND category_id=?";

			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, getCategoryID(category));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = extractCouponFromResultSet(resultSet);
				filteredCustomerCoupons.add(coupon);
			}
			return filteredCustomerCoupons;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get customer coupons by a category");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Coupon> filteredCustomerCoupons = new ArrayList<Coupon>();
		String sqlQuery = "SELECT * FROM coupons WHERE coupon_id IN (SELECT coupon_id FROM purchases WHERE customer_id=?) AND coupon_price <=?";

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setDouble(2, maxPrice);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Coupon coupon = extractCouponFromResultSet(resultSet);
				filteredCustomerCoupons.add(coupon);
			}
			return filteredCustomerCoupons;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get customer coupons with a max price");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public boolean isCouponExistsById(long couponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM coupons WHERE coupon_id = ?");

			preparedStatement.setLong(1, couponID);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				return true;
			}

			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.QUERY_ERROR, "Query to check if company exists failed");

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public boolean isTitleExists(long companyId, String title) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection
					.prepareStatement("SELECT * FROM COUPONS WHERE company_id = ? AND coupon_title = ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, title);
			result = preparedStatement.executeQuery();

			if (result.next()) {

				return true;
			}

			return false;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.QUERY_ERROR, "Failed to establish whether title exists");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public boolean isCouponExpired(Coupon coupon) throws ApplicationException {

		Date currentDate = new Date();
		if (coupon.getEnd_date().after(currentDate)) {

			return false;
		}
		return true;
	}

	private long getCategoryID(Category category) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = String.format("SELECT * FROM  CATEGORIES WHERE category_name=? ");

			preparedStatement = connection.prepareStatement(sqlStatement);

			preparedStatement.setString(1, category.name());

			result = preparedStatement.executeQuery();

			if (result.next()) {
				return result.getLong("category_id");
			}
			throw new ApplicationException(ErrorType.QUERY_ERROR, "Failed to get category Id");
		}

		catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.QUERY_ERROR, "Failed to get category Id");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	private static Category getCategoryString(int index) {
		return Category.values()[index - 1];
	}

	Coupon extractCouponFromResultSet(ResultSet result) throws ApplicationException, SQLException {
	
			Coupon coupon = new Coupon();
			coupon.setCouponId(result.getLong("coupon_id"));
			coupon.setCategory(getCategoryString((int) result.getLong("category_id")));
			coupon.setTitle(result.getString("coupon_title"));
			coupon.setDescription(result.getString("coupon_description"));
			coupon.setStart_date(result.getDate("coupon_start_date"));
			coupon.setEnd_date(result.getDate("coupon_end_date"));
			coupon.setAmount(result.getInt("coupon_amount"));
			coupon.setPrice(result.getDouble("coupon_price"));
			coupon.setImg(result.getString("coupon_image"));

			return coupon;
		
	}

}
