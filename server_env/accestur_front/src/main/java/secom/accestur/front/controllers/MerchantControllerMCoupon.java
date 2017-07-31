package secom.accestur.front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import secom.accestur.core.model.coupon.MerchantMCoupon;
import secom.accestur.core.service.impl.coupon.IssuerMCouponService;
import secom.accestur.core.service.impl.coupon.ManufacturerMCouponService;
import secom.accestur.core.service.impl.coupon.MerchantMCouponService;
import secom.accestur.core.service.impl.coupon.UserMCouponService;


@Controller
public class MerchantControllerMCoupon {
	

	MerchantMCoupon merchant;

	@Autowired
	@Qualifier("merchantmcouponService")
	MerchantMCouponService merchantService;
	
	@Autowired
	@Qualifier("manufacturermcouponService")
	ManufacturerMCouponService manufacturerService;
	
	@Autowired
	@Qualifier("usermcouponService")
	UserMCouponService userService;
	
	@Autowired
	@Qualifier("issuermcouponService")
	IssuerMCouponService issuerService;
	
	@RequestMapping(value = "/merchant/getParamsRedeem", method=RequestMethod.GET)
	@ResponseBody
	public String getParamsRedeem(){
		return merchantService.initRedeemParamsMCoupon();
	}
	
	@RequestMapping(value = "/merchant/getChallengeRedeem", method=RequestMethod.POST)
	@ResponseBody
	public String getChallengeRedeem(@RequestBody String json){
		
		String clear = issuerService.redeemingMCoupon(merchantService.sendingMCouponRedeem(json));
		
		merchantService.confirmationMCouponRedeem(clear);//FINALITZACIO DE REDEEM
		
		return manufacturerService.ClearingManufacturer(merchantService.initClearingMerchant(clear)); //FINALITZACIO DE CLEARING
		
	}

}