package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.UserMCouponRepository;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.model.coupon.UserMCoupon;
import secom.accestur.core.service.coupon.UserMCouponServiceInterface;
import secom.accestur.core.utils.Constants;

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
		params[0] = crypto.getSignature(username+password);
		params[1] = crypto.encryptWithPublicKey(username);
		params[2] = crypto.encryptWithPublicKey(password);
		return params;
	}
	
	public boolean verifyUsername(String[] params){
		boolean verified = crypto.getValidation(params[0]+params[1], params[2]);
		if (verified){
			UserMCoupon user = new UserMCoupon();
			user.setUsername(params[0]);
			user.setPassword(params[1]);
			//user.setManufacturerMCoupon(params[3]);
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
		
		String[] Xo = new String[p];
		String[] Yo = new String[q];
		
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
		
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//Date date;
		//try {
		//	date = (Date) dateFormat.parse(paramsMCoupon[2]);
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		params[5]=paramsMCoupon[2];
		
		//params[6]=paramsMCoupon[3];
		
		usermcouponRepository.save(user);
		
		return sendUserToIssuerPurchase(params);
	}
	
	private String[] solveMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[4];
		params[0] = json.getString("p");
		params[1] = json.getString("q");
		params[2] = json.getString("EXPDATE");
		//params[3] = json.getString("SN");	
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
		return json.toString();
	}
	
	public UserMCoupon getUserMCoupon(){
		return usermcouponRepository.findAll().iterator().next();
	}


	public void createCertificate(){
		crypto.initPrivateKey("cert/user/private_USER.der");
	}

	
	public UserMCoupon getUserMCouponByUsername(String username){
		return null;
	}
}