package secom.accestur.front.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;
import secom.accestur.core.service.impl.coupon.ManufacturerMCouponService;


@Controller
public class ManufacturerControllerMCoupon {
	

	ManufacturerMCoupon manufacturer;
	
	
	IssuerMCoupon issuer;
	
	MerchantMCoupon merchant;

	@Autowired
	@Qualifier("manufacturermcouponService")
	ManufacturerMCouponService manufacturerService;
	
	@RequestMapping(value = "/manufacturer/getChallengeRegister", method=RequestMethod.POST)
	@ResponseBody
	public String getChallenge(@RequestBody String json){
		return manufacturerService.generateUsername(json);
	}
	
	@RequestMapping(value = "/manufacturer/getParamsCoupon", method=RequestMethod.GET)
	@ResponseBody
	public String getParamsCoupon(){
		return manufacturerService.getOfferCoupon();
	}
	
}