package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.IssuerMCouponRepository;
import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.service.coupon.IssuerMCouponServiceInterface;

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
	
	@Autowired
	@Qualifier("mcouponService")
	private MCouponService mcouponService;

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
	
	public void newIssuerMCoupon(String name, ManufacturerMCoupon manufacturer){
		IssuerMCoupon i = getIssuerMCouponByName(name);
		if(i==null){
			IssuerMCoupon issuer = new IssuerMCoupon();
			issuer.setName(name);
			issuer.setManufacturerMCoupon(manufacturer);
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
			
			params[8]=paramsJson[7];
		
		return sendIssuerToManufacturerPurchase(params);
		}else{
			return "Failed Signature";
		}
	}
	
	private String[] solveUserMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[8];
		params[0] = json.getString("username");
		params[1] = json.getString("Xo");
		params[2] = json.getString("Yo");
		params[3] = json.getString("p");
		params[4] = json.getString("q");
		params[5] = json.getString("EXPDATE");
		params[6] = json.getString("signature");
		params[7] = json.getString("merchant");
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
		json.put("merchant", params[8]);
		return json.toString();
	}
	
	//Purchase 5 Issuer recieves coupon generated information.
public String getMCouponGeneratedByManufacturer(String json) {
		//Missing Issuer Signature.
		return json;
	}
///////////////////////////////////////////////////////////////////////
/////////////////// REDEEM COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////

//REDEEM 4 ISSUER MAKES VERIFICATION OF THE REDEEM COUPON.

public String redeemingMCoupon(String json) {
	
	crypto.initPublicKey("cert/issuer/public_ISSUER.der");
	MCoupon coupon = new MCoupon();
	String[] paramsJson = solveRedeemMCouponParams(json);
	//Validate Signature of the merchant
	if (crypto.getValidation(paramsJson[0]+paramsJson[1]+paramsJson[2]+paramsJson[3]+paramsJson[4]+paramsJson[5]+paramsJson[6], paramsJson[7])){
	System.out.println("NO ES SA PRIMERA CONDICIO");
	createCertificate();
	
	String[] params = new String[16];

	params[0] = paramsJson[8];//Id Merchant
	//Validate Signature of the User
	if (crypto.getValidation(paramsJson[6], paramsJson[5])){
	
	System.out.println("NO ES SA SEGONA CONDICIO");
		
	params[1] = paramsJson[0];//Rid
	
	params[2] = paramsJson[1];//Label
	
	params[3] = crypto.decryptWithPrivateKey(paramsJson[2]); //Xi Decrypted
	
	params[4] = crypto.decryptWithPrivateKey(paramsJson[3]); //IndexHash Decrypted
	
	params[5] = crypto.decryptWithPrivateKey(paramsJson[4]); //Sn Decrypted
	
	Integer sn = new Integer(params[5]);
	
	coupon = mcouponService.getMCouponBySn(sn);
	
	//Verification of Rid
	
	String nXo = coupon.getXo();
	
	String nRid = Cryptography.hash(params[0]+paramsJson[6]+nXo+params[3]);
	
	//FALTA VERIFICACIÓ QUE P SIGUI CORRECTE AMB L'INDEXHASH ARRIBAT.
	
	if (nRid.equals(params[1])){

	//Missing verification EXD.
		
	params[6] = nRid; //New Rid (Validation Signature)
		
	params[7]=crypto.getSignature(nRid); //Signature of issuer
	
	return sendIssuerToMerchantRedeem(params);
	}else{
		return "Failed Signature or Hash";
	}
	}else{
		return "Failed Signature";
	}
	}else{
		return "Failed Rid Verification";
	}
}

private String sendIssuerToMerchantRedeem(String[] params) {
	JSONObject json = new JSONObject();
	json.put("nrid", params[6]);
	json.put("signature", params[7]);
	json.put("rid", params[1]);
	json.put("idmerchant", params[0]);
	return json.toString();
}

private String[] solveRedeemMCouponParams (String message){
	JSONObject json = new JSONObject(message);
	String[] params = new String[9];
	params[0] = json.getString("rid");
	params[1] = json.getString("label");
	params[2] = json.getString("xi");
	params[3] = json.getString("indexhash");
	params[4] = json.getString("sn");
	params[5] = json.getString("signaturecustomer");
	params[6] = json.getString("username");
	params[7] = json.getString("signaturemerchant");
	params[8] = json.getString("idmerchant");
	//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
	return params;
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