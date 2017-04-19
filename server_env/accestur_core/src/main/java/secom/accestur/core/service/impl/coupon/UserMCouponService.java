package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.UserMCouponRepository;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.User;
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

	private String[] paramsOfPass;
	private String[] psi;
	private String K;
	private BigInteger random;
	private BigInteger RU;

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
			usermcouponRepository.save(user);
		}

		return verified;
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