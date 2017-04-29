package secom.accestur.core.service.impl.coupon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.MerchantMCouponRepository;
import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;
import secom.accestur.core.service.coupon.MerchantMCouponServiceInterface;

@Service("merchantmcouponService")
public class MerchantMCouponService implements MerchantMCouponServiceInterface{
	@Autowired
	@Qualifier("merchantmcouponRepository")
	private MerchantMCouponRepository merchantmcouponRepository;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	private MerchantMCoupon merchant;

	public void newMerchantMCoupon(String name, IssuerMCoupon issuer){
		MerchantMCoupon m = getMerchantMCouponByName(name);
		if(m == null){
			MerchantMCoupon merchant = new MerchantMCoupon();
			merchant.setName(name);
			merchant.setIssuerMCoupon(issuer);
			merchantmcouponRepository.save(merchant);
			System.out.println("Name: = " + merchant.getName());
		} else {
			System.out.println("This merchant already exisits, it will be initialized to the existing values");
		}
		
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// REDEEM COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//REDEEM 1 MERCHANT INICIALIZES THE COMMUNICATION.
	
	public String initRedeemParamsMCoupon(String nameMerchant){
		createCertificate();
		String[] params= new String [4];
		params[0]=nameMerchant;
		
		Random rand = new Random();
		Integer namec = rand.nextInt(50) + 1;
		params[1]=namec.toString();
		
		//Signature
		params[2]=crypto.getSignature(params[0]+params[1]);
		return sendInitRedeemMCouponMessage(params);
	}
	
	private String sendInitRedeemMCouponMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("name", params[0]);
		json.put("namec", params[1]);
		json.put("signature", params[2]);
		return json.toString();
	}
		
	public MerchantMCoupon getMerchantMCouponByName(String name){
		return merchantmcouponRepository.findByNameIgnoreCase(name);
	}
	
	public List<MerchantMCoupon> getMerchantMCouponByIssuerMCoupon(IssuerMCoupon issuer){
		return merchantmcouponRepository.findByIssuer(issuer);
	}

	public String verifyMCoupon(){
		return null;
	}

	public String[] authenticateMerchantMCoupon(String[] params){
		return null;
	}

	public String[] verifyMCoupon(String[] params){
		return null;
	}

	public void createCertificate(){
		crypto.initPrivateKey("private_ISSUER.der");
		crypto.initPublicKey("public_ISSUER.der");		
	}
	
}