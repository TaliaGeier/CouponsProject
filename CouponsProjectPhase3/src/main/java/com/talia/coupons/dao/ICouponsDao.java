package com.talia.coupons.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talia.coupons.entities.Company;
import com.talia.coupons.entities.Coupon;

@Repository

public interface ICouponsDao extends CrudRepository<Coupon, Long> {

		public Coupon findByTitle(String title);
		
		public List<Coupon> findByCompany(Company company);
		
		public List<Coupon> findByCompanyAndCategory(Company company, String category);
		
		@Query(value = "SELECT * FROM coupons WHERE coupon_price <= :couponMaxPrice", nativeQuery = true)
		public List<Coupon> getAllCouponsByPrice(@Param("couponMaxPrice") Double couponMaxPrice);
		
		@Query(value = "SELECT * FROM coupons WHERE coupon_category = :category", nativeQuery = true)
		public List<Coupon> getAllCouponsByCategory(@Param("category") String category);

		@Query(value = "SELECT * FROM coupons WHERE company_company_id = :companyId", nativeQuery = true)
		public List<Coupon> getAllCouponsByCompany(@Param("companyId") long companyId);

		@Query(value = "SELECT * FROM coupons WHERE coupon_id IN (SELECT coupon_coupon_id FROM purchases WHERE customer_user_user_id = :customerId)", nativeQuery = true)
		public List<Coupon> getAllCouponsByCustomer(@Param("customerId") long customerId);

		@Modifying
		@Query(value = "DELETE FROM coupons WHERE company_company_id = :companyId", nativeQuery = true)
		public void deleteCouponsByCompanyId(@Param("companyId") long companyId);
	

}
