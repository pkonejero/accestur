package secom.accestur.core.service.impl;

import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.UserRepository;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.User;
import secom.accestur.core.service.UserServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("userService")
public class UserService implements UserServiceInterface{
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Autowired
	@Qualifier("serviceAgentService")
	private ServiceAgentService serviceAgentService;

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

	public String getUserByPseudonym1(String pseudonym){
		User user = userRepository.findAll().iterator().next();
		if (user != null) {
			user.setPseudonym(pseudonym);
			userRepository.save(user);
		}

		return pseudonym;
	}

	public User getUser(){
		return userRepository.findAll().iterator().next();
	}

	public User getUserByPseudonym(String pseudonym){
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
			User user = new User();
			user.setPseudonym(generatePseudonym(params[0], params[1]));
			user.setSchnorr(schnorr.getPrivateCertificate());
			userRepository.save(user);
		}

		return verified;
	}

	public void createCertificate(){

		crypto.initPrivateKey("cert/user/private_USER.der");
	}

	public String[] authenticateUser(){
		crypto.initPublicKey("cert/ttp/public_TTP.der");
		//crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		schnorr.Init();
		schnorr.SecretKey();
		schnorr.PublicKey();
		String params[] = new String[3];
		BigInteger y = schnorr.getY();
		params[0] = crypto.getSignature(y.toString());
		params[1] = crypto.encryptWithPublicKey(y.toString());

		return params;
	}

	public String getService(){
		User user = userRepository.findAll().iterator().next();
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

	public String solveChallenge(String c, String[] services){
		schnorr.solveChallengeQuery(new BigInteger(c), RU);
		K = Cryptography.hash(schnorr.getW2().toString());
		random = schnorr.getRandom();
		psi = new String[services.length];
		for (int i = 0; i < services.length; i++) {
			ServiceAgent service = serviceAgentService.getServiceByName(services[i]);
			if (service.getM() != -1) {
				for (int j = 0; j < service.getM(); j++) {
					if (j == 0) {
						psi[i] = Cryptography.hash(random.toString());
					} else {
						psi[i] = Cryptography.hash(psi[i]);
					}
				}
			} else {
				psi[i] = "Infinite uses";
			}
		}
		String[] ws = new String[2];
		ws[0] = schnorr.getW1().toString();
		ws[1] = schnorr.getW2().toString();

		return solveChallengeMessage(psi, services, ws);
	}

	private String solveChallengeMessage(String[] psi, String[] services, String[] ws){
		JSONObject json = new JSONObject();
		json.put("w1",crypto.encryptWithPublicKey( ws[0]));
		json.put("w2",crypto.encryptWithPublicKey( ws[1]));
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;

		for (int i = 0; i < psi.length; i++){
			jsonObject = new JSONObject();
			jsonObject.put("service", services[i]);
			jsonObject.put("psi", psi[i]);
			String s = crypto.encryptWithPublicKey(jsonObject.toString());
			jsonArray.put(i, s);
		}

		json.put("services", jsonArray);
		String message = json.toString();

		return message;
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