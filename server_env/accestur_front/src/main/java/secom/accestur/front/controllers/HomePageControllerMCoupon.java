package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.service.impl.coupon.IssuerMCouponService;
import secom.accestur.core.service.impl.coupon.ManufacturerMCouponService;
import secom.accestur.core.service.impl.coupon.MerchantMCouponService;
import secom.accestur.core.service.impl.coupon.UserMCouponService;

@Controller
public class HomePageControllerMCoupon{
	@Autowired
	@Qualifier("usermcouponService")
	UserMCouponService usermcouponService;

	@Autowired
	@Qualifier("merchantmcouponService")
	MerchantMCouponService merchantmcouponService;

	@Autowired
	@Qualifier("issuermcouponService")
	IssuerMCouponService issuermcouponService;
	
	@Autowired
	@Qualifier("manufacturermcouponService")
	ManufacturerMCouponService manufacturermcouponService;


	@RequestMapping("/mcoupon")
	public String welcome(Map<String, Object> model){
		Init();
		//generateUser();
		//passPurchase();
		return "mcoupon";
	}

	private void Init(){
		manufacturermcouponService.newManufacturerMCoupon("AccesturManufacturer");
		issuermcouponService.newIssuerMCoupon("AccesturIssuer");
		merchantmcouponService.newMerchantMCoupon("AccesturMerchant", issuermcouponService.getIssuerMCouponByName("AccesturIssuer"));
		//createServices("Accestur");
	}
}