package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.MCouponRepository;
import secom.accestur.core.dao.coupon.ManufacturerMCouponRepository;
import secom.accestur.core.dao.coupon.UserMCouponRepository;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.coupon.CounterMCoupon;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;
import secom.accestur.core.model.coupon.UserMCoupon;
import secom.accestur.core.service.coupon.ManufacturerMCouponServiceInterface;

@Service("manufacturermcouponService")
public class ManufacturerMCouponService implements ManufacturerMCouponServiceInterface{	
	@Autowired
	@Qualifier("manufacturermcouponRepository")
	private ManufacturerMCouponRepository manufacturermcouponRepository;
	
	@Autowired
	@Qualifier("usermcouponRepository")
	private UserMCouponRepository usermcouponRepository;
	
	@Autowired
	@Qualifier("mcouponRepository")
	private MCouponRepository mcouponRepository;
	
	@Autowired
	@Qualifier("mcouponService")
	private MCouponService mcouponService;
	
	@Autowired
	@Qualifier("usermcouponService")
	private UserMCouponService usermcouponService;
	
	@Autowired
	@Qualifier("merchantmcouponService")
	private MerchantMCouponService merchantmcouponService;
	
	@Autowired
	@Qualifier("countermcouponService")
	private CounterMCouponService countermcouponService;

	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	//Values necessary to create Coupons;
	
	private ManufacturerMCoupon manufacturer;
	
	
	private String[] paramsOfCoupon;
	private BigInteger X0;
	private BigInteger Y0;


	public void createCertificate(){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		crypto.initPublicKey("cert/user/public_USER.der");	
	}

	public void newManufacturerMCoupon(String name){
		ManufacturerMCoupon i = getManufacturerMCouponByName(name);
		if(i==null){
			ManufacturerMCoupon manufacturer = new ManufacturerMCoupon();
			manufacturer.setName(name);
			System.out.println("Name: = " + manufacturer.getName());
			manufacturermcouponRepository.save(manufacturer);		
		} else {
			System.out.println("This manufacturer already exisits, it will be initialized to the existing values");
		}
	}
	
	public ManufacturerMCoupon getManufacturerMCouponByName(String name){
		return manufacturermcouponRepository.findByNameIgnoreCase(name);
	}
	
	public String generateUsername(String input){
		manufacturer = manufacturermcouponRepository.findAll().iterator().next(); //WE OBTAIN THE ONLY MANUFACTURER THAT EXISTS
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");//IT SHOULD BE MANUFACTURER's PK.
		JSONObject json = new JSONObject(input);
		
		String signature = json.getString("signature");
		String username = json.getString("username");
		String password = json.getString("password");
		password = crypto.decryptWithPrivateKey(password);
		//if(user==null){
			if (crypto.getValidation(username+password, signature)){
			
				UserMCoupon user = new UserMCoupon();
			
				user.setUsername(username);
				user.setPassword(password);
				user.setManufacturerMCoupon(manufacturer);
				usermcouponRepository.save(user);
			
				return "OKAY";
			} else {
				return "REGISTERINGERROR";
			}
		//}//else{
		//	return "ALREADYREGISTERED";
		//}
	}
	
	public String logIn(String input){
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
		JSONObject json = new JSONObject(input);
		String username = json.getString("username");
		String password = json.getString("password");
		String signature = json.getString("signature");
		password = crypto.decryptWithPrivateKey(password);
		
		if(crypto.getValidation(username+password, signature)){
			
			UserMCoupon compUser = usermcouponService.getUserMCouponByUsername(username);

			if(compUser!=null){
				String compPassword = compUser.getPassword();
				if(password.equals(compPassword)&&username.equals(compUser.getUsername())){
					return "OKAY";
				}else{
					return "PASSWORDINCORRECT";
				}
			}else{
				return "REGISTEREDBEFORELOGIN";
			}
		}else{
			return "FAILEDSIGNATURE";
		}
		
	}
	///////////////////////////////////////////////////////////////////////
	/////////////////// CREATE OFFER COUPON///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	public String initOfferCoupon(Integer p, Integer q, Date EXD,MerchantMCoupon merchant){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		MCoupon coupon = new MCoupon();
		String[] params= new String [5];
		params[0]=p.toString();
		params[1]=q.toString();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = EXD;
		System.out.println("EXD:" + dateFormat.format(date));
		params[2] = dateFormat.format(date);
		//Signature
		params[3]=crypto.getSignature(p.toString()+q.toString());
		//Merchant
		params[4]=merchant.getName();
		//coupon.setExpDate((java.sql.Date) date);
		coupon.setP(p);
		coupon.setQ(q);
		coupon.setMerchant(merchant);
		mcouponService.saveMCoupon(coupon);
		return sendInitMCouponMessage(params);
	}
	
	public String getOfferCoupon(){
		List<MCoupon> MCoupons = getAll();
		JSONArray message = new JSONArray();
		JSONObject json;
		for (int i  = 0; i < MCoupons.size(); i++){
			json = new JSONObject();
			json.put("p", MCoupons.get(i).getP());
			json.put("q", MCoupons.get(i).getQ());
			json.put("merchant", MCoupons.get(i).getMerchant().toString());
			json.put("id", MCoupons.get(i).getId());
			message.put(json);
		}
		//MCoupon coupon=mcouponService.getMCouponBySn(i);
		//String[] params= new String [5];
		//params[0]=coupon.getP().toString();
		//params[1]=coupon.getQ().toString();
		//params[2]=coupon.getMerchant().toString();
		return message.toString();
		
	}
	
	private String sendOfferCoupon(String[] params) {
		JSONObject json = new JSONObject();
		json.put("p", params[0]);
		json.put("q", params[1]);
		//json.put("EXPDATE", params[2]);
		//json.put("signature", params[3]);
		json.put("merchant", params[2]);
		JSONArray message = new JSONArray();
		message.put(json);
		return message.toString();
	}
	
	public List<MCoupon> getAll(){
		return mcouponRepository.findAll();
	}
	
	///////////////////////////////////////////////////////////////////////
	/////////////////// PURCHASE COUPON///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	//PURCHASE 1 COUPON INIT PARAMS//
	
	public String initParamsMCoupon(Integer p, Integer q, Date EXD,MerchantMCoupon merchant){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		MCoupon coupon = new MCoupon();
		String[] params= new String [5];
		params[0]=p.toString();
		params[1]=q.toString();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = EXD;
		System.out.println("EXD:" + dateFormat.format(date));
		params[2] = dateFormat.format(date);
		//Signature
		params[3]=crypto.getSignature(p.toString()+q.toString());
		//Merchant
		params[4]=merchant.getName();
		return sendInitMCouponMessage(params);
	}
	
	private String sendInitMCouponMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("p", params[0]);
		json.put("q", params[1]);
		json.put("EXPDATE", params[2]);
		json.put("signature", params[3]);
		json.put("merchant", params[4]);
		return json.toString();
	}
	
	public void errorIssuing1(){
		System.out.println("Error Sended By The User Validating Digital Signature");
	}
	
	//PURCHASE 4 COUPON Receiving from Issuer all info of the Coupon//
	
	public String getCoupon(String json) {
		crypto.initPublicKey("cert/user/public_USER.der");
		String[] paramsJson = solveIssuerMCouponParams(json);
		String[] params = new String[15];
		//Validation of the User
		if (crypto.getValidation(paramsJson[0]+paramsJson[1]+paramsJson[2]+paramsJson[3]+paramsJson[4], paramsJson[9])){ //+paramsJson[5]
			System.out.println("S'HA VALIDAD LA FIRMA DE USUARI AL MANUFACTURER");
			crypto.initPublicKey("cert/issuer/public_ISSUER.der");
			//Validation of the Issuer
		if (crypto.getValidation(paramsJson[6], paramsJson[7])){
			
			System.out.println("S'HA VALIDAD LA FIRMA DE ISSUER AL MANUFACTURER");
			
		Integer p = new Integer(paramsJson[3]);
		Integer q = new Integer(paramsJson[4]);
		
		Integer sn = new Integer(paramsJson[6]);
		
		UserMCoupon user = usermcouponService.getUserMCouponByUsername(params[0]);
		
		params[0]= paramsJson[1];
		
		params[1] = paramsJson[2];
		
		params[2] = sn.toString();
		
		params[3] = p.toString();
		
		params[4] = q.toString();
		
		params[5] = user.getUsername();
		
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
		params[6]=crypto.getSignature(params[0]+params[1]+params[3]+params[4]);//+paramsJson[5]
		
		params[7] = paramsJson[8];
		
		//params[8]=paramsJson[5];
		params[10]=paramsJson[10];
		System.out.println("S'HA ACABAT LA SEGONA FASE");
		return sendFinishMCouponPurchase(params);
		
		}else{
			return "FAILED";
		}
		}else{
			return "FAILED";
		}
	}

	private String[] solveIssuerMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[15];
		params[0] = json.getString("username");
		params[1] = json.getString("Xo");
		params[2] = json.getString("Yo");
		params[3] = json.getString("p");
		params[4] = json.getString("q");
		//params[5] = json.getString("EXPDATE");
		params[6] = json.getString("sn");
		params[7] = json.getString("signatureIssuer");
		params[8] = json.getString("merchant");
		params[9] = json.getString("signatureUser");
		params[10] = json.getString("id");
		return params;
	}
	
	private String sendFinishMCouponPurchase(String[] params) {
		JSONObject json = new JSONObject();
		json.put("xo", params[0]);
		json.put("yo", params[1]);
		json.put("sn", params[2]);
		json.put("p", params[3]);
		json.put("q", params[4]);
		json.put("username", params[5]);
		json.put("signature", params[6]);
		json.put("merchant", params[7]);
		//json.put("EXD",params[8]);
		json.put("id", params[10]);
		return json.toString();
	}
	
	public void errorIssuing4(){
		System.out.println("Error Sended By The Issuer Validating Digital Signature");
	}
	
///////////////////////////////////////////////////////////////////////
/////////////////// CLEARING COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////
	
	//CLEARING 2 MANUFACTURER RECEIVES INFORMATION
	
public String ClearingManufacturer(String json) {
		
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		
		String[] paramsJson = solveMerchantClearingConfirmation(json);
		
		String[] params = new String[16];
		
		if (crypto.getValidation(paramsJson[0], paramsJson[2])){
			//GETTING VALUES FOR THE COUPON
			
			crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
			
			params[2] = crypto.decryptWithPrivateKey(paramsJson[3]); // Xi to clear
			params[3] = crypto.decryptWithPrivateKey(paramsJson[4]); // indexHash to clear
			params[4] = crypto.decryptWithPrivateKey(paramsJson[5]); // sn to clear
			
			Integer sn = new Integer(params[4]);
			Integer indexHash = new Integer(params[3]);
			
			MCoupon coupon = mcouponService.getMCouponBySn(sn);
			CounterMCoupon counter = coupon.getCounter();
			counter.setCounterMCoupon(indexHash-1);
			countermcouponService.saveCounterMCoupon(counter);
			
			Integer newCounter = indexHash-1;
			System.out.println("THIS IS THE NEW COUNTER="+newCounter);
			
			
		}else{
			return "FAILED";//return "FAILED SIGNATURE ISSUER";
		}
		if (crypto.getValidation(paramsJson[0], paramsJson[1])){
			
		
			crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
			params[0]=crypto.getSignature(paramsJson[0]); 
			params[1]=paramsJson[0];//NO S'HAURIA D'ENVIAR(GUARDAR)
		
		return sendClearingToMerchant(params);
		}else{
			return "FAILED";//return "FAILED SIGNATURE MERCHANT";
		}
	}
	
	private String[] solveMerchantClearingConfirmation (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[9];
		params[0] = json.getString("rid");
		params[1] = json.getString("signaturemerchant");
		params[2] = json.getString("signatureissuer");
		params[3] = json.getString("xi");
		params[4] = json.getString("indexhash");
		params[5] = json.getString("sn");
		return params;
	}
	
	private String sendClearingToMerchant(String[] params) {
		JSONObject json = new JSONObject();
		json.put("signaturemanufacturer", params[0]);
		return json.toString();
		}
	
	public void errorClearing2(){
		System.out.println("Error Sended By The Merchant Validating RID of the Manufacturer");
	}
	
	
	
	
	
}

