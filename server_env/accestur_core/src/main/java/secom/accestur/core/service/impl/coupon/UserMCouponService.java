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
import secom.accestur.core.model.coupon.UserMCoupon;
import secom.accestur.core.service.coupon.UserMCouponServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("usermcouponService")
public class UserMCouponService implements UserMCouponServiceInterface{
	@Autowired
	@Qualifier("usermcouponRepository")
	private UserMCouponRepository usermcouponRepository;

	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	private String[] paramsOfPass;
	private String[] psi;
	private String K;
	private BigInteger random;
	private BigInteger RU;

	public String getUserMCouponByPseudonym1(String pseudonym){
		UserMCoupon user = usermcouponRepository.findAll().iterator().next();
		if (user != null) {
			user.setPseudonym(pseudonym);
			usermcouponRepository.save(user);
		}

		return pseudonym;
	}

	public UserMCoupon getUserMCoupon(){
		return usermcouponRepository.findAll().iterator().next();
	}

	public UserMCoupon getUserMCouponByPseudonym(String pseudonym){
		return null;
	}

	public String showProof(){
		return null;
	}

	public boolean getValidationConfirmation(){
		return false;
	}

	public String receivePass(){
		return null;
	}

	public String sendPass(){
		return null;
	}

	public boolean verifyPseudonym(String[] params){
		boolean verified = crypto.getValidation(params[0], params[1]);
		if (verified){
			UserMCoupon user = new UserMCoupon();
			user.setPseudonym(generatePseudonym(params[0], params[1]));
			user.setSchnorr(schnorr.getPrivateCertificate());
			usermcouponRepository.save(user);
		}

		return verified;
	}

	public void createCertificate(){
		//schnorr.Init();
		//schnorr.SecretKey();
		//schnorr.PublicKey();
		crypto.initPrivateKey("cert/user/private_USER.der");
	}

	public String[] authenticateUserMCoupon(){
		//crypto.initPublicKey("cert/ttp/public_TTP.der");
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		String params[] = new String[3];
		BigInteger y = schnorr.getY();
		params[0] = crypto.getSignature(y.toString());
		params[1] = crypto.encryptWithPublicKey(y.toString());

		return params;
	}

	public String getService(){
		UserMCoupon user = usermcouponRepository.findAll().iterator().next();
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
		String[] params = new String[8];
		params[0] = user.getPseudonym();
		params[1] = schnorr.getCertificate();
		RU = schnorr.getRandom();
		params[2] = Cryptography.hash(RU.toString());
		BigInteger Hu = schnorr.getPower(RU);
		schnorr.getServiceQuery();
		params[3] = Hu.toString();
		params[4] = schnorr.getA_1().toString();
		params[5] = schnorr.getA_2().toString();
		params[6] = Constants.LIFETIME;
		params[7] = Constants.CATEGORY;

		return getServiceMessage(params);
	}

	public String[] showTicket(){
		return null;
	}

	public String[] showPass(){
		return null;
	}

	public String[] showProof(String[] params){
		return null;
	}


	public String[] receivePass(String[] params){
		return null;
	}

	private static String generatePseudonym(String y, String signature){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("y", y);
		jsonObject.put("signature", signature);

		return jsonObject.toString();
	}

	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);

		return jsonObject.getString("y");
	}

	private String getServiceMessage(String[] params){
		JSONObject json = new JSONObject();
		json.put("user", params[0]);
		json.put("certificate", params[1]);
		json.put("hRU", params[2]);
		json.put("Hu", params[3]);
		json.put("A1", params[4]);
		json.put("A2", params[5]);
		json.put("Lifetime", params[6]);
		json.put("Category", params[7]);
		
		return json.toString();
	}
}