package secom.accestur.core.service.impl;

import com.activeandroid.query.Select;

import java.math.BigInteger;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


import secom.accestur.core.crypto.Cryptography;
import secom.accestur.core.crypto.Schnorr;
import secom.accestur.core.dao.UserMCouponRepository;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.UserMCoupon;
import secom.accestur.core.service.UserMCouponServiceInterface;
import secom.accestur.core.utils.Constants;


public class UserMCouponService implements UserMCouponServiceInterface{

	private UserMCouponRepository usermcouponRepository;
	
	//@Autowired
	//@Qualifier("cryptography")
	private Cryptography crypto;
	
	//@Autowired
	//@Qualifier("schnorr")
	private Schnorr schnorr;
	
	//@Autowired
	//@Qualifier("mcouponService")
	private MCouponService mcouponService;
	
	//@Autowired
	//@Qualifier("manufacturermcouponService")
	//private ManufacturerMCouponService manufacturermcouponService;
	
	private UserMCoupon user;
	
	private String MC;
	private BigInteger X;
	private BigInteger Y;
	private String Rid;
	
	public UserMCouponService() {
		mcouponService = new MCouponService();
		//manufacturermcouponService = new ManufacturerMCouponService();
		schnorr = new Schnorr();
		crypto = new Cryptography();
	}

	public String getUserMCouponByUsername1(String username){
		//UserMCoupon user = usermcouponRepository.findAll().iterator().next();
		if (user != null) {
			user.setUsername(username);
			storeUserMCoupon(user);
		}

		return username;
	}
	
	public String authenticateUsername(String username, String password){
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		String params[] = new String[3];
		params[0] = crypto.getSignature(username+password);//Firma la faig damunt les dades que vull autenticar.
		params[1] = username; //username
		params[2] = crypto.encryptWithPublicKey(password);
		return sendUserToManufacturerRegister(params);
	}
	
	private String sendUserToManufacturerRegister(String[] params) {
		JSONObject json = new JSONObject();
		//json.put("signature", params[0]);
		//json.put("username", params[1]);
		//json.put("password", params[2]);
		return json.toString();
	}

	public void setUserCoupon(Long id,UserMCoupon user) {
			JSONObject json = new JSONObject();
			MCoupon mCoupon = new Select().from(MCoupon.class).where("id = ? ", id).executeSingle();

			mCoupon.setUser(user);
			storeUserMCoupon(user);

//			activation.setActDate(json.getString("ACTDate"));
//			activation.setState(json.getString("State"));
//			activation.setSignature(json.getString("Signature"));

//			System.out.println("Activation saved at: " + activation.save());
//			return true;
//		} catch (JSONException e) {
//			return false;
//		}
	}
	
	//public String verifyUsername(String[] params){
		//boolean verified = crypto.getValidation(getUserMCoupon().getUsername(), params[0]);
		//if (verified){
		//	return "REGISTERED USER COMPLETED";
		//}else{
		//	return "REGISTERED USER FAILED";
		//}
	//}
	
///////////////////////////////////////////////////////////////////////
/////////////////// PURCHASE COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//PURCHASE 2 COUPON Sending to Issuer Info-Coupon,Signature//
	
	public void generateUserParamsCoupon(int p, int q, UserMCoupon user,Date EXPDATE) {
		crypto.initPublicKey("cert/issuer/public_ISSUER.der"); //Haure de posar la del Manufacturer
		//String[] paramsMCoupon = solveMCouponParams(json);
		//if (crypto.getValidation(paramsMCoupon[0]+paramsMCoupon[1], paramsMCoupon[3])){
		Integer pN=p;
		Integer qN=q;
		String[] Xo = new String[p+1];
		String[] Yo = new String[q+1];
		
		//user = usermcouponRepository.findAll().iterator().next();
		String[] params = new String[10];
		params[0] = user.getUsername();

		X = schnorr.getRandom();
		System.out.println("AQUEST ES X PER PROVAR==="+X);
		user.setX(X.toString()); //SAVING PRIVATE NUMBER OF THE USER
		Xo[0]=X.toString();
		for (int i = 1; i <= p; i++){
			Xo[i] = Cryptography.hash(Xo[i-1].toString());
		}
		params[1] = Xo[p];

		user.setXo(Xo[p]);
		
		System.out.println("AQUEST ES XO PER PROVAR==="+Xo[p]);
		
		Y = schnorr.getRandom();
		System.out.println("AQUEST ES Y PER PROVAR==="+Y);
		user.setY(Y.toString()); //SAVING PRIVATE NUMBER OF THE USER
		Yo[0]=Y.toString();
		for (int i = 1; i <= q; i++){
			Yo[i] = Cryptography.hash(Yo[i-1].toString());
		}
		params[2] = Yo[q];

		user.setYo(Yo[p]);
		
		System.out.println("AQUEST ES YO PER PROVAR==="+Yo[p]);
		
		params[3] = pN.toString();
		
		params[4] = qN.toString();
		
		params[5]=EXPDATE.toString();
		
		//Signature of the User
		createCertificate(); //Init Private Key User
		params[6]=crypto.getSignature(params[0]+params[1]+params[2]+params[3]+params[4]+params[5]);
		user.setSignature(params[6]);
		
		//System.out.println("FIRMA USUARI=="+params[6]);
		//Merchant
		//params[7]=paramsMCoupon[4];
		
		//MC=params[1]+params[2]+params[3]+params[4];//+params[5] HEM LLEVAT EXPIRATION DATE.
		
		storeUserMCoupon(user); //GUARDAM ELS PARAMETRES X, Y I ENVIAM XO I YO
		
		//return sendUserToIssuerPurchase(params);
		//}
		//else{
		//	return "FAILED";
		//}
	}
	
	private String[] solveMCouponParams (String message){
		//JSONObject json = new JSONObject(message);
		String[] params = new String[5];
		//params[0] = json.getString("p");
		//params[1] = json.getString("q");
		//params[2] = json.getString("EXPDATE");
		//params[3] = json.getString("signature");
		//params[4] = json.getString("merchant");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
//	private String sendUserToIssuerPurchase(String[] params) {
////		JSONObject json = new JSONObject();
////		json.put("username", params[0]);
////		json.put("Xo", params[1]);
////		json.put("Yo", params[2]);
//		//json.put("EXPDATE", params[5]);
//		//json.put("signature", params[6]);
//		//json.put("merchant", params[7]);
//		//return json.toString();
//	}
	//PURCHASE 6 COUPON Receiving Coupon from Issuer//
	public String recieveMCoupon(String params) {
		
		String[] paramsJson = solveFinishMCouponIssue(params);
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		if (crypto.getValidation(MC+paramsJson[1], paramsJson[0])){
		
		return "ISSUING PHASE FINISHED CORRECTLY";
		
		}else{
			return "FAILED";
		}
	}
	
	private String[] solveFinishMCouponIssue (String message){
		//JSONObject json = new JSONObject(message);
		String[] params = new String[15];
		//params[0] = json.getString("signature");
		//params[1] = json.getString("sn");
		return params;
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// REDEEM COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//REDEEM 2 USER RECIEVES THE INFORMATION ABOUT THE MERCHANT
	
	public String initRedeemMCoupon(Integer sn,UserMCoupon user,Integer indexHash, String json){
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveRedeemMCouponParams(json);
		
		if (crypto.getValidation(paramsJson[0]+paramsJson[1], paramsJson[2])){
		MCoupon coupon = new MCoupon();
		
		//coupon = mcouponService.getMCouponBySn(sn);
		
		String[] params= new String [15];
		//ID Merchant
		params[0]=paramsJson[0];
		
		//ID Customer
		params[1]=user.getUsername();
		
		//X0
		params[2]=coupon.getXo();
		
		//Xi
		//String X = getUserMCouponByUsername(user.getUsername()).getX();
		
		System.out.println("AQUESTA ES X DEL USER COUPON REDEEM:"+X);
		
		Integer p = coupon.getP();
		String[] Xi = new String[p+1];
		//Xi[0]=X;
		for (int i = 1; i<=indexHash;i++){
			Xi[i]=Cryptography.hash(Xi[i-1]);
		}
		params[3]=Xi[indexHash];
		
		System.out.println("XI SENDED BY THE USER="+params[3]);
		
		//R_id (Send)
		params[4]=Cryptography.hash(params[0]+params[1]+params[2]+params[3]);
		
		Rid=params[4];
		
		//Label (Send)
		params[5]=Cryptography.hash(paramsJson[0]+paramsJson[1]);
		
		//Encryption 
		params[6]=crypto.encryptWithPublicKey(params[3]);
		params[7]=crypto.encryptWithPublicKey(indexHash.toString());
		params[8]=crypto.encryptWithPublicKey(sn.toString());
		
		crypto.initPrivateKey("cert/user/private_USER.der");
		//Signature
		params[9]=crypto.getSignature(params[4]+params[5]+params[6]+params[7]+params[8]);//Signing all the message 2.
		
		return sendUserToMerchantRedeem(params);
		}else{
			return "FAILED";
		}
	}
	
	private String[] solveRedeemMCouponParams (String message){
		//JSONObject json = new JSONObject(message);
		String[] params = new String[4];
		//params[0] = json.getString("name");
		//params[1] = json.getString("namec");
		//params[2] = json.getString("signature");
		//System.out.println("AQUETS ES EL RESULTAT DEL JSON ARRIBAT"+" "+params[0]+params[1]+params[2]+params[3]);
		return params;
	}
	
	private String sendUserToMerchantRedeem(String[] params) {
		JSONObject json = new JSONObject();
		//json.put("idmerchant", params[0]);
		//json.put("username", params[1]);
		//json.put("rid", params[4]);
		//json.put("label", params[5]);
		//json.put("xi", params[6]);
		//json.put("indexhash", params[7]);
		//json.put("sn", params[8]);
		//json.put("signature", params[9]);
		return json.toString();
	}
	
	public void errorRedeem2(){
		System.out.println("Error Sended By The Merchant Validating Digital Signature USER");
	}
	
	//REDEEM 6 USER RECIEVES THE CONFIRMATION OF THE ISSUER AND MERCHANT.
	
public String confirmationMCouponRedeem2(String json) {
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveConfirmationRedeemMCoupon(json);
		//Validate Signature of the Merchant
		if (crypto.getValidation(Rid, paramsJson[2])){
		//Validate Signature of the ISSUER
		if(crypto.getValidation(Rid, paramsJson[3])){
			
		return "REDEEM COMPLETED";
		}else{
			return "FAILED";//return "Failed Signature of the Merchant";
		}
		}else{
			return "FAILED";//return "Failed Verification of the Issuer";
		}
	}

	private String[] solveConfirmationRedeemMCoupon (String message){
		//JSONObject json = new JSONObject(message);
		String[] params = new String[4];
		//params[2] = json.getString("signaturemerchant");
		//params[3] = json.getString("signatureissuer");
		return params;
	}
	
	
	//public UserMCoupon getUserMCoupon(){
	//	return usermcouponRepository.findAll().iterator().next();
	//}
	
	//public void initUserMCoupon() {
	//	user = getUserMCoupon();
	//}

	public void createCertificate() {
		crypto.initPrivateKey(Constants.PATH_PRIVATE_KEY);
		//crypto.initPublicKey(Constants.PATH_PROVIDER_KEY);
	}

	public void storeUserMCoupon(UserMCoupon userMCoupon) {
		this.user = userMCoupon;
		saveUserMCoupon();
	}

	public void saveUserMCoupon(){
		user.save();
	}
	
	public UserMCoupon getUserMCouponByUsername(String username){
		return new Select().from(UserMCoupon.class).where("username = ? ", username).executeSingle();
	}
}