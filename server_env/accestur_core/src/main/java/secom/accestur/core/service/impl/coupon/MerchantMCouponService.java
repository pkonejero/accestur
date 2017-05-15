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
	
	private Integer namec;

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
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		String[] params= new String [4];
		params[0]=nameMerchant;
		
		Random rand = new Random();
		namec = rand.nextInt(50) + 1;
		params[1]=namec.toString();
		
		//Signature
		params[2]=crypto.getSignature(params[0]+params[1]);
		
		System.out.println("FIRMA:"+params[2]);
		return sendInitRedeemMCouponMessage(params);
	}
	
	private String sendInitRedeemMCouponMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("name", params[0]);
		json.put("namec", params[1]);
		json.put("signature", params[2]);
		return json.toString();
	}
	
	//REDEEM 3 MERCHANT RECIEVES INFORMATION OF THE USER.
	
	public String sendingMCouponRedeem(String json) {
		crypto.initPublicKey("cert/user/public_USER.der");
		String[] paramsJson = solveRedeemMCouponParams(json);
		
		//LABEL NOW
		String label = Cryptography.hash(paramsJson[7]+namec);
		System.out.println("LABEL NOW:"+label);
		System.out.println("LABEL BEFORE:"+paramsJson[1]);
		//Validate Signature of the user
		if (crypto.getValidation(paramsJson[6], paramsJson[5])&&paramsJson[1].equals(label)){ //PRIMER VERIFIACIó DE LA FIRMA i DESPRES EL LABEL
			
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
		String[] params = new String[16];

		params[0] = paramsJson[0];//Rid
		
		params[1] = paramsJson[1];//Label
		
		params[2] = paramsJson[2];//Xi Encrypted
		
		params[3] = paramsJson[3]; //indexhash Encrypted
		
		params[4] = paramsJson[4]; //sn Encrypted
		
		params[5] = paramsJson[5]; //Signature Customer
		
		params[6] = paramsJson[6]; //Username(Validation Signature)
			
		params[7]=crypto.getSignature(params[0]+params[1]+params[2]+params[3]+params[4]+params[5]+params[6]); //Signature of merchant
		
		params[8]=paramsJson[7];//Id Merchant. MIRAR SI ES NECESSÀRI.
		
		return sendRedeemMCouponMessageToManufacturer(params);
		}else{
			return "Failed Signature or Hash";
		}
		
	}
	
	private String sendRedeemMCouponMessageToManufacturer(String[] params) {
		JSONObject json = new JSONObject();
		json.put("rid", params[0]);
		json.put("label", params[1]);
		json.put("xi", params[2]);
		json.put("indexhash", params[3]);
		json.put("sn", params[4]);
		json.put("signaturecustomer", params[5]);
		json.put("username", params[6]);
		json.put("signaturemerchant", params[7]);
		json.put("idmerchant", params[8]);
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
		params[5] = json.getString("signature");
		params[6] = json.getString("username");
		params[7] = json.getString("idmerchant");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
	//REDEEM 5 MERCHANT CONFIRMS THE REDEEM COUPON.
	public String confirmationMCouponRedeem(String json) {
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveConfirmationMCouponRedeem(json);
		//Validate Signature of the ISSUER
		if (crypto.getValidation(paramsJson[0], paramsJson[1])){
		//Verification of Old Rid and New Rid
		if(paramsJson[0].equals(paramsJson[2])){
			
		String[] params = new String[16];

		params[0] = paramsJson[1];//Proof of Signature of Issuer
			
		params[1]=crypto.getSignature(paramsJson[3]); //Signature of Merchant
		
		params[2]=paramsJson[3];//Id Merchant
		
		params[3]=paramsJson[0]; // New Rid for confirmation of the Signature of the Issuer.
		
		return sendRedeemMCouponConfirmationToManufacturer(params);
		}else{
			return "Failed Signature or Hash";
		}
		}else{
			return "Failed Verification of Rid";
		}
	}
	
	private String sendRedeemMCouponConfirmationToManufacturer(String[] params) {
		JSONObject json = new JSONObject();
		json.put("nrid", params[3]);
		json.put("idmerchant", params[2]);
		json.put("signaturemerchant", params[1]);
		json.put("signatureissuer", params[0]);
		return json.toString();
	}
	
	private String[] solveConfirmationMCouponRedeem (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[9];
		params[0] = json.getString("nrid");
		params[1] = json.getString("signature");
		params[2] = json.getString("rid");
		params[3] = json.getString("idmerchant");
		params[4] = json.getString("xi");
		params[5] = json.getString("indexhash");
		params[6] = json.getString("sn");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// CLEARING COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//CLEARING 1 Sending clearing request to the manufacturer.
	
	public String initClearingMerchant(String json) {
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
		String[] paramsJson = solveConfirmationMCouponRedeem(json);
			
		String[] params = new String[16];

		params[0] = paramsJson[2];//Rid
			
		params[1]=crypto.getSignature(params[0]); //Signature of Merchant
		
		params[2]=paramsJson[1];//Signature of Rid of the Issuer
		
		params[3] = paramsJson[4]; // Xi encrypted for the manufacturer
		
		params[4] = paramsJson[5]; // IndexHash encrypted for the manufacturer
		
		params[5] = paramsJson[6]; // SN encrypted for the manufacturer
		
		return sendClearingToManufacturer(params);
		
	}
	
	private String sendClearingToManufacturer(String[] params) {
	JSONObject json = new JSONObject();
	json.put("rid", params[0]);
	json.put("signaturemerchant", params[1]);
	json.put("signatureissuer", params[2]);
	json.put("xi", params[3]);
	json.put("indexhash", params[4]);
	json.put("sn", params[5]);
	return json.toString();
	}
	
	//CLEARING 3 END OF CLEARING PHASE
	
	public String ClearingMCoupon(String json) {
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveConfirmationClearing(json);
		
		if (crypto.getValidation(paramsJson[1], paramsJson[0])){
			
		return "CLEARING COMPLETED";
		
		}else{
			return "FAILED SIGNATURE";
		}
		
	}
	
	private String[] solveConfirmationClearing (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[9];
		params[0] = json.getString("signaturemanufacturer");
		params[1] = json.getString("rid");
		return params;
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