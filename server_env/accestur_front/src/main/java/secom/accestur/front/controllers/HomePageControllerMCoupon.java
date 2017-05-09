package secom.accestur.front.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.model.coupon.UserMCoupon;
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
		generateUser();
		issuingMCoupon();
		redeemMCoupon();
		clearingMCoupon();
		return "mcoupon";
	}

	private void Init(){
		manufacturermcouponService.newManufacturerMCoupon("AccesturManufacturer");
		issuermcouponService.newIssuerMCoupon("AccesturIssuer",manufacturermcouponService.getManufacturerMCouponByName("AccesturManufacturer"));
		merchantmcouponService.newMerchantMCoupon("AccesturMerchant", issuermcouponService.getIssuerMCouponByName("AccesturIssuer"));
	}
	
	private void generateUser(){
		usermcouponService.createCertificate();
		manufacturermcouponService.createCertificate();
		System.out.println(usermcouponService.verifyUsername(manufacturermcouponService.generateUsername(usermcouponService.authenticateUsername("Toni","toni1992"))));
	}
	
	private void issuingMCoupon(){
		usermcouponService.createCertificate();
		manufacturermcouponService.createCertificate();
		issuermcouponService.createCertificate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(26/07/1992);
		System.out.println(manufacturermcouponService.getCoupon(issuermcouponService.getInitMCouponMessage(usermcouponService.getInitMCouponMessage(manufacturermcouponService.initParamsMCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant"))))));
	}
	
	private void redeemMCoupon(){

		usermcouponService.initUserMCoupon();

		System.out.println(usermcouponService.confirmationMCouponRedeem(merchantmcouponService.confirmationMCouponRedeem(issuermcouponService.redeemingMCoupon(merchantmcouponService.sendingMCouponRedeem(usermcouponService.initRedeemMCoupon(1,"Toni",3,merchantmcouponService.initRedeemParamsMCoupon("AccesturMerchant")))))));
	}
	
	private void clearingMCoupon(){
		
	}
}