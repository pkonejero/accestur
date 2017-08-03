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
		createOffer();
		//issuingMCoupon();
		//redeemMCoupon();
		return "mcoupon";
	}

	private void Init(){
		manufacturermcouponService.newManufacturerMCoupon("AccesturManufacturer");
		issuermcouponService.newIssuerMCoupon("AccesturIssuer",manufacturermcouponService.getManufacturerMCouponByName("AccesturManufacturer"));
		merchantmcouponService.newMerchantMCoupon("AccesturMerchant", issuermcouponService.getIssuerMCouponByName("AccesturIssuer"));
	}
	
	private void createOffer(){
		
		//usermcouponService.createCertificate();
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
		
		manufacturermcouponService.initOfferCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant"));
		manufacturermcouponService.initOfferCoupon(10, 15, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant"));
		
		//System.out.println(manufacturermcouponService.initParamsMCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant")));
	}
	
	private void generateUser(){
		usermcouponService.createCertificate();
		manufacturermcouponService.createCertificate();
		System.out.println(manufacturermcouponService.generateUsername(usermcouponService.authenticateUsername("Toni","toni1992")));
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
		//System.out.println(usermcouponService.recieveMCoupon(issuermcouponService.getMCouponGeneratedByManufacturer(manufacturermcouponService.getCoupon(issuermcouponService.getInitMCouponMessage(usermcouponService.getInitMCouponMessage(manufacturermcouponService.initParamsMCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant"))))))));
		
		String prooferror = usermcouponService.getInitMCouponMessage(manufacturermcouponService.initParamsMCoupon(5, 6, date,merchantmcouponService.getMerchantMCouponByName("AccesturMerchant")));
		
		if (!prooferror.equals("FAILED")){
			prooferror=issuermcouponService.getInitMCouponMessage(prooferror);
			if(!prooferror.equals("FAILED")){
				prooferror= manufacturermcouponService.getCoupon(prooferror);
				if(!prooferror.equals("FAILED")){
					prooferror= issuermcouponService.getMCouponGeneratedByManufacturer(prooferror);
					if(!prooferror.equals("FAILED")){
						System.out.println(usermcouponService.recieveMCoupon(prooferror));
					}else{
						issuermcouponService.errorIssuing5();
					}
				}else{
					manufacturermcouponService.errorIssuing4();
				}
			}
			else{
				issuermcouponService.errorIssuing3();
			}
		}else{
			manufacturermcouponService.errorIssuing1();
		}
		
	}
	
//	private void redeemMCoupon(){
//		usermcouponService.createCertificate();
//		manufacturermcouponService.createCertificate();
//		issuermcouponService.createCertificate();
//		
//		usermcouponService.initUserMCoupon();
//		//String clear = issuermcouponService.redeemingMCoupon(merchantmcouponService.sendingMCouponRedeem(usermcouponService.initRedeemMCoupon(1,usermcouponService.getUserMCouponByUsername("Toni"),1,merchantmcouponService.initRedeemParamsMCoupon("AccesturMerchant"))));
//		//System.out.println(usermcouponService.confirmationMCouponRedeem2( merchantmcouponService.confirmationMCouponRedeem(clear)));
//		
//		String prooferror = usermcouponService.initRedeemMCoupon(1,usermcouponService.getUserMCouponByUsername("Toni"),1,merchantmcouponService.initRedeemParamsMCoupon("AccesturMerchant"));
//		String clear;
//		
//		if(!prooferror.equals("FAILED")){
//			prooferror= merchantmcouponService.sendingMCouponRedeem(prooferror);
//			if(!prooferror.equals("FAILED")){
//				prooferror= issuermcouponService.redeemingMCoupon(prooferror);
//				clear = prooferror;
//				if(!prooferror.equals("FAILED")){
//					prooferror= merchantmcouponService.confirmationMCouponRedeem(prooferror);
//					if(!prooferror.equals("FAILED")){
//						prooferror=usermcouponService.confirmationMCouponRedeem2(prooferror);
//						if(!prooferror.equals("FAILED")){
//							System.out.println("REDEEM FINISH CORRECTLY");
//							//Starting CLEARING PHASE IF THE REDEEM WAS CORRECT
//							
//							prooferror=merchantmcouponService.initClearingMerchant(clear);
//							
//							if(!prooferror.equals("FAILED")){							
//								prooferror=manufacturermcouponService.ClearingManufacturer(prooferror);
//								if(!prooferror.equals("FAILED")){
//									prooferror=merchantmcouponService.ClearingMCoupon(prooferror);
//									if(!prooferror.equals("FAILED")){
//										System.out.println("CLEARING PHASE FINISHED CORRECTLY");
//									}else{
//										manufacturermcouponService.errorClearing2();
//									}
//								}else{
//									merchantmcouponService.errorClearing1();
//								}
//							}else{
//								//Inicialitzam Clearing per tant ,no error.
//							}
//							
//						
//						}else{
//							merchantmcouponService.errorRedeem5();
//						}
//					}else{
//						issuermcouponService.errorRedeem4();
//					}
//				}else{
//					merchantmcouponService.errorRedeem3();
//				}
//			}else{
//				usermcouponService.errorRedeem2();
//			}
//		}else{
//			merchantmcouponService.errorRedeem1();
//			
//		}
		
		//START CLEARING
		//System.out.println(merchantmcouponService.ClearingMCoupon(manufacturermcouponService.ClearingManufacturer(merchantmcouponService.initClearingMerchant(clear))));
//	}
	
}