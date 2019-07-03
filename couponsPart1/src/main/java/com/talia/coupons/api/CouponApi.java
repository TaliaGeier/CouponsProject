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

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.enums.Category;
import com.talia.coupons.enums.ErrorType;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.logic.CouponController;

@RestController
@RequestMapping("/coupons")
public class CouponApi {

	@Autowired
	private CouponController couponController;

	@PostMapping
	public long addCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		return couponController.addCoupon(coupon);
	}

	@GetMapping("/{couponId}")
	public Coupon getOneCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {

		return couponController.getOneCoupon(couponId);
	}

	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponController.getAllCoupons();
	}

	@GetMapping("/byMaxPrice")
	public List<Coupon> getAllCouponsByMaxPrice(@RequestParam("maxPrice") Double price) throws ApplicationException {

		return couponController.getAllCouponsByMaxPrice(price);
	}

	@GetMapping("/byCategory")

	public List<Coupon> getAllCouponsByCategory(@RequestParam("category") Category category)
			throws ApplicationException {

		return couponController.getAllCouponsByCategory(category);
	}

	@GetMapping("/byCompany")
	public List<Coupon> getCompanyCoupons(@RequestParam("companyId") long companyId) throws ApplicationException {
		return couponController.getCompanyCoupons(companyId);
	}

	@GetMapping("/byCompany/category")
	public List<Coupon> getCompanyCouponsByCategory(@RequestParam("companyId") long companyId,
			@RequestParam("category") Category category) throws ApplicationException {

		return couponController.getCompanyCouponsByCategory(companyId, category);
	}

	@GetMapping("/byCompany/maxPrice")
	public List<Coupon> getCompanyCouponsByMaxPrice(@RequestParam("companyId") long companyId,
			@RequestParam("maxPrice") double maxPrice) throws ApplicationException {

		return couponController.getCompanyCouponsByMaxPrice(companyId, maxPrice);
	}

	@GetMapping("/byCustomer")
	public List<Coupon> getCustomerCoupons(@RequestParam("customerId") long customerId) throws ApplicationException {

		return couponController.getCustomerCoupons(customerId);
	}

	@GetMapping("/byCustomer/category")
	public List<Coupon> getCustomerCouponsByCategory(@RequestParam("customerId") long customerId,
			@RequestParam("category") Category category) throws ApplicationException {
		return couponController.getCustomerCouponsByCategory(customerId, category);
	}

	@GetMapping("/byCustomer/maxPrice")
	public List<Coupon> getCustomerCouponsByMaxPrice(@RequestParam("customerId") long customerId,
			@RequestParam("maxPrice") Double maxPrice) throws ApplicationException {
		return couponController.getCustomerCouponsByMaxPrice(customerId, maxPrice);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponController.updateCoupon(coupon);
	}

	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		couponController.deleteCoupon(couponId);
	}

	
}
