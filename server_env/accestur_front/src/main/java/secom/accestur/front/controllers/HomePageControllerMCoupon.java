package secom.accestur.front.controllers;

import java.text.DateFormat;
import java.text.ParseException;
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
		//Init();
		//generateUser();
		issuingMCoupon();
		//redeemMCoupon();
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
		String data= "26/07/1992";
		Date date = new Date();
		try {
			date = dateFormat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DATE=="+ dateFormat.format(date));
		System.out.println(usermcouponService.recieveMCoupon(issuermcouponService.getMCouponGeneratedByManufacturer(manufacturermcouponService.getCoupon(issuermcouponService.getInitMCouponMessage(usermcouponService.getInitMCouponMessage(manufacturermcouponService.initParamsMCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant"))))))));
	}
	
	private void redeemMCoupon(){
		usermcouponService.createCertificate();
		manufacturermcouponService.createCertificate();
		issuermcouponService.createCertificate();
		
		usermcouponService.initUserMCoupon();
		String clear = issuermcouponService.redeemingMCoupon(merchantmcouponService.sendingMCouponRedeem(usermcouponService.initRedeemMCoupon(1,usermcouponService.getUserMCouponByUsername("Toni"),1,merchantmcouponService.initRedeemParamsMCoupon("AccesturMerchant"))));
		System.out.println(usermcouponService.confirmationMCouponRedeem2( merchantmcouponService.confirmationMCouponRedeem(clear)));
		//START CLEARING
		System.out.println(merchantmcouponService.ClearingMCoupon(manufacturermcouponService.ClearingManufacturer(merchantmcouponService.initClearingMerchant(clear))));
	}
	
}