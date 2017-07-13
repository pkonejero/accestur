package secom.accestur.front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.service.impl.coupon.IssuerMCouponService;
import secom.accestur.core.service.impl.coupon.ManufacturerMCouponService;


@Controller
public class IssuerControllerMCoupon {
	

	IssuerMCoupon issuer;

	@Autowired
	@Qualifier("issuermcouponService")
	IssuerMCouponService issuerService;
	
	@Autowired
	@Qualifier("manufacturermcouponService")
	ManufacturerMCouponService manufacturerService;
	
	@RequestMapping(value = "/issuer/setUserCoupon", method=RequestMethod.POST)
	@ResponseBody
	public String setUserCoupon(@RequestBody String json){
		return issuerService.getMCouponGeneratedByManufacturer(manufacturerService.getCoupon(issuerService.getInitMCouponMessage(json)));
	}

}