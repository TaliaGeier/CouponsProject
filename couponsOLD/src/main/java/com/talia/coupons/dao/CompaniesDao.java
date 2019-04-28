package com.talia.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.talia.coupons.beans.Company;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;

import com.talia.coupons.interfaces.ICompaniesDao;
import com.talia.coupons.utils.JdbcUtils;

public class CompaniesDao implements ICompaniesDao {
	CouponsDao couponsDao = new CouponsDao();

	public long addCompany(Company company) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "INSERT INTO companies (company_name ,company_email) VALUES (?,?)";
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				long id = result.getLong(1);
				company.setId(id);
				return id;
			}

			throw new ApplicationException(ErrorType.INSERTION_ERROR, "Failed to add Company");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.INSERTION_ERROR, "Failed to add Company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public void updateCompany(Company company) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "UPDATE COMPANIES SET company_name=?, company_email=? WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.setLong(3, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.UPDATE_ERROR, "Failed to update company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCompany(long companyID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlStatement = "DELETE FROM COMPANIES WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.DELETE_ERROR, "Failed to delete company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Company> getAllCompanies() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Company company = null;
		List<Company> allCompanies = new ArrayList<Company>();

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM companies";
			preparedStatement = connection.prepareStatement(sqlStatement);
			result = preparedStatement.executeQuery();
			while (result.next()) {
				company = extractCompanyFromResultSet(result);
				allCompanies.add(company);
			}

			return allCompanies;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get all companies");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public Company getOneCompany(long companyID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM companies WHERE company_id =?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyID);
			result = preparedStatement.executeQuery();
			if (!result.next()) {
				return null;
			}
			return extractCompanyFromResultSet(result);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get company");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	@Override
	public String getCompanyDetails(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = "SELECT * FROM COMPANIES WHERE company_id=?";
			preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, companyId);

			result = preparedStatement.executeQuery();

			if (result.next()) {
				return extractCompanyFromResultSet(result).toString();
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to get company's details");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public boolean isCompanyExistsById(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM companies WHERE COMPANY_ID = ?");

			preparedStatement.setLong(1, companyId);

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

	public boolean isCompanyExistsByName(String companyName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM COMPANIES WHERE company_name = ?");

			preparedStatement.setString(1, companyName);

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

	public boolean isCompanyExistsByEmail(String email) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM COMPANIES WHERE company_email = ?");

			preparedStatement.setString(1, email);

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

	private Company extractCompanyFromResultSet(ResultSet result) throws ApplicationException {
		try {
			Company company = new Company();
			company.setId(result.getLong("company_id"));
			company.setName(result.getString("company_name"));
			company.setEmail(result.getString("company_email"));
			return company;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.READ_ERROR, "Failed to extract company");
		}

	}

}
