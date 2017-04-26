package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.catalina.authenticator.Constants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.ManufacturerMCouponRepository;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.service.coupon.ManufacturerMCouponServiceInterface;
import secom.accestur.core.service.impl.MCityPassService;

@Service("manufacturermcouponService")
public class ManufacturerMCouponService implements ManufacturerMCouponServiceInterface{	
	@Autowired
	@Qualifier("manufacturermcouponRepository")
	private ManufacturerMCouponRepository manufacturermcouponRepository;
	
	@Autowired
	@Qualifier("mCouponService")
	private MCouponService mCouponService;

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
	
	public String[] generateUsername(String[] params){
		manufacturer = manufacturermcouponRepository.findAll().iterator().next();
		String[] message = new String[4];
		String username = crypto.decryptWithPrivateKey(params[1]);
		String password = crypto.decryptWithPrivateKey(params[2]);
		if (crypto.getValidation(username+password, params[0])){
			message[0] = username;
			message[1] = password;
			message[2] = crypto.getSignature(username+password);
			message[3] = manufacturer.getName();
			
		} else {
			message[0] = "Error";
		}
		return message;
	}
	///////////////////////////////////////////////////////////////////////
	/////////////////// PURCHASE COUPON///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	//PURCHASE 1 COUPON INIT PARAMS//
	
	public String initParamsMCoupon(Integer p, Integer q, Date EXD){
		String[] params= new String [3];
		params[0]=p.toString();
		params[1]=q.toString();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = EXD;
		System.out.println("EXD:" + dateFormat.format(date));
		params[2] = dateFormat.format(date);
		//params[3] = sn.toString();
		return sendInitMCouponMessage(params);
	}
	
	private String sendInitMCouponMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("p", params[0]);
		json.put("q", params[1]);
		json.put("EXPDATE", params[2]);
		//json.put("SN", params[3]);
		return json.toString();
	}
	
	//PURCHASE 4 COUPON Receiving from Issuer all info of the Coupon//
	
	public String getCoupon(String json) {
		
		String[] paramsJson = solveIssuerMCouponParams(json);
		
		MCoupon coupon = new MCoupon();
		
		String[] params = new String[10];
		//coupon.setUser(paramsJson[0]); //Hauria de ser l'objecte User
		
		coupon.setXo(paramsJson[1]);
		coupon.setYo(paramsJson[2]);
		
		Integer p = new Integer(paramsJson[3]);
		Integer q = new Integer(paramsJson[4]);
		
		coupon.setP(p);
		coupon.setQ(q);
		
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				//Date date;
				//try {
				//	date = (Date) dateFormat.parse(paramsJson[5]);
				//} catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
				
		//coupon.setExpDate();
		
		Integer sn = new Integer(paramsJson[6]);
		
		coupon.setSn(sn);
		
		mCouponService.saveMCoupon(coupon);
		
		return coupon.toString();
	}

	private String[] solveIssuerMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[6];
		params[0] = json.getString("username");
		params[1] = json.getString("Xo");
		params[2] = json.getString("Yo");
		params[3] = json.getString("p");
		params[4] = json.getString("q");
		params[5] = json.getString("EXPDATE");
		params[6] = json.getString("sn");
		return params;
	}
	
	
}