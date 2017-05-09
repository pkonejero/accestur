package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.UserMCouponRepository;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.model.coupon.UserMCoupon;
import secom.accestur.core.service.coupon.UserMCouponServiceInterface;

@Service("usermcouponService")
public class UserMCouponService implements UserMCouponServiceInterface{
	@Autowired
	@Qualifier("usermcouponRepository")
	private UserMCouponRepository usermcouponRepository;
	
	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;
	
	@Autowired
	@Qualifier("mcouponService")
	private MCouponService mcouponService;
	
	@Autowired
	@Qualifier("manufacturermcouponService")
	private ManufacturerMCouponService manufacturermcouponService;
	
	private UserMCoupon user;
	
	private String[] paramsOfPass;
	private String[] psi;
	private String K;
	private BigInteger random;
	private BigInteger X;
	private BigInteger Y;

	public String getUserMCouponByUsername1(String username){
		UserMCoupon user = usermcouponRepository.findAll().iterator().next();
		if (user != null) {
			user.setUsername(username);
			usermcouponRepository.save(user);
		}

		return username;
	}
	
	public String[] authenticateUsername(String username, String password){
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		String params[] = new String[3];
		params[0] = crypto.getSignature(username+password);//Firma la faig damunt les dades que vull autenticar.
		params[1] = crypto.encryptWithPublicKey(username); //No és necessari xifrar username.
		params[2] = crypto.encryptWithPublicKey(password);
		return params;
	}
	
	public boolean verifyUsername(String[] params){
		boolean verified = crypto.getValidation(params[0]+params[1], params[2]);
		if (verified){
			UserMCoupon user = new UserMCoupon();
			user.setUsername(params[0]);
			user.setPassword(params[1]);
			user.setManufacturerMCoupon(manufacturermcouponService.getManufacturerMCouponByName(params[3]));
			usermcouponRepository.save(user);
		}

		return verified;
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// PURCHASE COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//PURCHASE 2 COUPON Sending to Issuer Info-Coupon,Signature//
	
	public String getInitMCouponMessage(String json) {
		crypto.initPublicKey("cert/issuer/public_ISSUER.der"); //Haure de posar la del Manufacturer
		String[] paramsMCoupon = solveMCouponParams(json);
		Integer p = new Integer(paramsMCoupon[0]);
		Integer q = new Integer(paramsMCoupon[1]);
		if (crypto.getValidation(paramsMCoupon[0]+paramsMCoupon[1], paramsMCoupon[3])){
		createCertificate();
		String[] Xo = new String[p+1];
		String[] Yo = new String[q+1];
		
		user = usermcouponRepository.findAll().iterator().next();
		String[] params = new String[10];
		params[0] = user.getUsername();

		X = schnorr.getRandom();
		user.setX(X.toString());
		Xo[0]=X.toString();
		for (int i = 1; i <= p; i++){
			Xo[i] = Cryptography.hash(Xo[i-1].toString());
		}
		params[1] = Xo[p];
		
		Y = schnorr.getRandom();
		user.setY(Y.toString());
		Yo[0]=Y.toString();
		for (int i = 1; i <= q; i++){
			Yo[i] = Cryptography.hash(Yo[i-1].toString());
		}
		params[2] = Yo[q];
		
		params[3] = p.toString();
		
		params[4] = q.toString();
		
		params[5]=paramsMCoupon[2];
		
		//Signature of the User
		
		params[6]=crypto.getSignature(params[3]+params[4]);
		
		//Merchant
		params[7]=paramsMCoupon[4];
		
		usermcouponRepository.save(user);
		
		return sendUserToIssuerPurchase(params);
		}
		else{
			return "Failed Signature";
		}
	}
	
	private String[] solveMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[5];
		params[0] = json.getString("p");
		params[1] = json.getString("q");
		params[2] = json.getString("EXPDATE");
		params[3] = json.getString("signature");
		params[4] = json.getString("merchant");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
	private String sendUserToIssuerPurchase(String[] params) {
		JSONObject json = new JSONObject();
		json.put("username", params[0]);
		json.put("Xo", params[1]);
		json.put("Yo", params[2]);
		json.put("p", params[3]);
		json.put("q", params[4]);
		json.put("EXPDATE", params[5]);
		json.put("signature", params[6]);
		json.put("merchant", params[7]);
		return json.toString();
	}
	//PURCHASE 6 COUPON Receiving Coupon from Issuer//
	public String recieveMCoupon(String params) {
		System.out.println(params);
		JSONObject json = new JSONObject(params);
		long sn = json.getLong("sn");
		//secretValueService.saveSecretValue(new SecretValue(mCityPassService.getMCityPassBySn(id),
		//		serviceAgentService.getServiceByName(service).getProvider(), random.toString()));
		return "Everything OK";
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// REDEEM COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//REDEEM 2 USER RECIEVES THE INFORMATION ABOUT THE MERCHANT
	
	public String initRedeemMCoupon(Integer sn,String username,Integer indexHash, String json){
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveRedeemMCouponParams(json);
		
		if (crypto.getValidation(paramsJson[0]+paramsJson[1], paramsJson[2])){
		MCoupon coupon = new MCoupon();
		
		coupon = mcouponService.getMCouponBySn(sn);
		
		System.out.println("AQUESTA ES XO DES COUPON REDEEM:"+coupon.getXo());
		String[] params= new String [15];
		//ID Merchant
		params[0]=paramsJson[0];
		
		//ID Customer
		params[1]=username;
		
		//X0
		params[2]=coupon.getXo();
		
		//Xi
		String X = getUserMCouponByUsername(username).getX();
		
		System.out.println("AQUESTA ES X DEL USER COUPON REDEEM:"+X);
		
		Integer p = coupon.getP();
		String[] Xi = new String[p+1];
		Xi[0]=X;
		for (int i = 1; i<=indexHash;i++){
			Xi[i]=Cryptography.hash(Xi[i-1]);
		}
		params[3]=Xi[indexHash];
		
		//R_id (Send)
		params[4]=Cryptography.hash(params[0]+params[1]+params[2]+params[3]);
		
		//Label (Send)
		params[5]=Cryptography.hash(paramsJson[0]+paramsJson[1]);
		
		//Encryption 
		params[6]=crypto.encryptWithPublicKey(params[3]);
		params[7]=crypto.encryptWithPublicKey(indexHash.toString());
		params[8]=crypto.encryptWithPublicKey(sn.toString());
		
		//Signature
		params[9]=crypto.getSignature(username);//Firmar tot el missatge 2.
		
		//namec
		params[10]= paramsJson[1];//PARAMETRE A ELIMINAR.
		
		return sendUserToMerchantRedeem(params);
		}else{
			return "Failed Signature";
		}
	}
	
	private String[] solveRedeemMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[4];
		params[0] = json.getString("name");
		params[1] = json.getString("namec");
		params[2] = json.getString("signature");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
	private String sendUserToMerchantRedeem(String[] params) {
		JSONObject json = new JSONObject();
		json.put("idmerchant", params[0]);
		json.put("username", params[1]);
		json.put("namec", params[10]);
		json.put("rid", params[4]);
		json.put("label", params[5]);
		json.put("xi", params[6]);
		json.put("indexhash", params[7]);
		json.put("sn", params[8]);
		json.put("signature", params[9]);
		return json.toString();
	}
	
	//REDEEM 6 USER RECIEVES THE CONFIRMATION OF THE ISSUER AND MERCHANT.
	
public String confirmationMCouponRedeem(String json) {
		
		crypto.initPublicKey("cert/user/public_ISSUER.der");
		
		String[] paramsJson = solveConfirmationRedeemMCoupon(json);
		//Validate Signature of the Merchant
		if (crypto.getValidation(paramsJson[1], paramsJson[2])){
		//Validate Signature of the ISSUER
		if(crypto.getValidation(paramsJson[0], paramsJson[3])){
			
		return "REDEEM COMPLETED";
		}else{
			return "Failed Signature of the Merchant";
		}
		}else{
			return "Failed Verification of the Issuer";
		}
	}

	private String[] solveConfirmationRedeemMCoupon (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[4];
		params[0] = json.getString("nrid");
		params[1] = json.getString("idmerchant");
		params[2] = json.getString("signaturemerchant");
		params[3] = json.getString("signatureissuer");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
	
	public UserMCoupon getUserMCoupon(){
		return usermcouponRepository.findAll().iterator().next();
	}
	
	public void initUserMCoupon() {
		user = getUserMCoupon();
	}

	public void createCertificate(){
		crypto.initPrivateKey("cert/user/private_USER.der");
	}

	
	public UserMCoupon getUserMCouponByUsername(String username){
		return usermcouponRepository.findAll().iterator().next();
	}
}