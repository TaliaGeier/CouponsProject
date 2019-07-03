package com.talia.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talia.coupons.beans.Coupon;
import com.talia.coupons.beans.Purchases;
import com.talia.coupons.exceptions.ApplicationException;
import com.talia.coupons.logic.PurchaseController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {
	@Autowired
	private PurchaseController purchaseController;
	
	@PostMapping
	public void addPurchase(@RequestBody Purchases purchase) throws ApplicationException {
		purchaseController.addPurchase(purchase);
	}

	@GetMapping("/{purchaseId}")
	public Purchases getPurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		return purchaseController.getOnePurchase(purchaseId);
	}

	@GetMapping
	public List<Purchases> getAllPurchases() throws ApplicationException {
		return purchaseController.getAllPurchases();
	}
	
	@GetMapping("/byCustomer")
	public List<Purchases> getPurchasesByCystomerId(@RequestParam("customerId") long customerId) throws ApplicationException {
		return purchaseController.getPurchasesByCystomerId(customerId);
	}

	@DeleteMapping("/{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		purchaseController.deletePurchase(purchaseId);
	}
}

