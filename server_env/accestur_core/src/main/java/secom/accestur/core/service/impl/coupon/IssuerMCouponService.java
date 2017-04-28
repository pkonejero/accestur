package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.IssuerMCouponRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.service.coupon.IssuerMCouponServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("issuermcouponService")
public class IssuerMCouponService implements IssuerMCouponServiceInterface{	
	@Autowired
	@Qualifier("issuermcouponRepository")
	private IssuerMCouponRepository issuermcouponRepository;


	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	//Values necessary to create Coupons;
	private String[] paramsOfPass;
	private BigInteger yU_c;
	private BigInteger Hu_c;

	private String[] ws;
	private String[] services;
	private String[] psi;

	public IssuerMCoupon getIssuerMCouponByName(String name){
		return issuermcouponRepository.findByNameIgnoreCase(name);
	}

	public void createCertificate(){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		crypto.initPublicKey("cert/user/public_USER.der");	
	}
	
	public void newIssuerMCoupon(String name){
		IssuerMCoupon i = getIssuerMCouponByName(name);
		if(i==null){
			IssuerMCoupon issuer = new IssuerMCoupon();
			issuer.setName(name);
			System.out.println("Name: = " + issuer.getName());
			issuermcouponRepository.save(issuer);		
		} else {
			System.out.println("This issuer already exisits, it will be initialized to the existing values");
		}
	}

	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}


	
	///////////////////////////////////////////////////////////////////////
	/////////////////// PURCHASE COUPON///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	//Purchase 3 Issuer recieves information Coupon and sends to manufacturer, generating SN
	
	public String getInitMCouponMessage(String json) {
		createCertificate();
		String[] paramsJson = solveUserMCouponParams(json);
		
		String[] params = new String[10];
	
		params[0] = paramsJson[0];

		params[1] = paramsJson[1];
		
		params[2] = paramsJson[2];
		
		params[3] = paramsJson[3];
		
		params[4] = paramsJson[4];
		
		params[5]=paramsJson[5];
		
		//L'unica cosa que fa es afegir un SN
		Integer sn = 123456789;
		params[6]=sn.toString();
		
		if (crypto.getValidation(params[3]+params[4], paramsJson[6])){
			
			params[7]=crypto.getSignature(params[3]+params[4]);
		
		return sendIssuerToManufacturerPurchase(params);
		}else{
			return "Failed Signature";
		}
	}
	
	private String[] solveUserMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[7];
		params[0] = json.getString("username");
		params[1] = json.getString("Xo");
		params[2] = json.getString("Yo");
		params[3] = json.getString("p");
		params[4] = json.getString("q");
		params[5] = json.getString("EXPDATE");
		params[6] = json.getString("signature");
		return params;
	}
	
	private String sendIssuerToManufacturerPurchase(String[] params) {
		JSONObject json = new JSONObject();
		json.put("username", params[0]);
		json.put("Xo", params[1]);
		json.put("Yo", params[2]);
		json.put("p", params[3]);
		json.put("q", params[4]);
		json.put("EXPDATE", params[5]);
		json.put("sn", params[6]);
		json.put("signature", params[7]);
		return json.toString();
	}
	
	//Purchase 5 Issuer recieves coupon generated information.
public String getMCouponGeneratedByManufacturer(String json) {
		//Missing Issuer Signature.
		return json;
	}
	

	@Override
	public String getChallenge(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPASS(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] verifyTicket(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean arrayGeneration() {
		// TODO Auto-generated method stub
		return false;
	}

}