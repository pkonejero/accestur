package secom.accestur.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.ProviderServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("providerService")
public class ProviderService implements ProviderServiceInterface {
	@Autowired
	@Qualifier("providerRepository")
	private ProviderRepository providerRepository;

	@Autowired
	@Qualifier("mCityPassService")
	private MCityPassService mCityPassService;

	@Autowired
	@Qualifier("activationService")
	private ActivationService activationService;

	@Autowired
	@Qualifier("rightOfUseService")
	private RightOfUseService rightOfUseService;

	@Autowired
	@Qualifier("counterService")
	private CounterService counterService;

	@Autowired
	@Qualifier("serviceAgentService")
	private ServiceAgentService serviceAgentService;

	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	BigInteger Hu_C;
	String[] kandRI;
	long timestamp1;
	long timestamp2;
	BigInteger Ap;

	public Provider getProviderByName(String name) {
		return providerRepository.findByNameIgnoreCase(name);
	}

	public List<Provider> getProvidersByIssuer(Issuer issuer) {
		return providerRepository.findByIssuer(issuer);
	}
	
	public Provider getProviderById(Long id){
		 return providerRepository.findById(id);
	}
	public void createCertificate() {
		crypto.initPrivateKey("private_ISSUER.der");
		crypto.initPublicKey("public_ISSUER.der");
	}

	///////////////////////////////////////////////////////////////////////
	/////////////////// PROVIDER AFFILIATION////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public void newProvider(String name, Issuer issuer) {
		Provider p = getProviderByName(name);
		if (p == null) {
			Provider provider = new Provider();
			provider.setName(name);
			provider.setIssuer(issuer);
			providerRepository.save(provider);
			System.out.println("Name: = " + provider.getName());
		} else {
			System.out.println("This provider already exisits, it will be initialized to the existing values");
		}

	}

	public ServiceAgent[] authenticateProvider(String[] names, int[] counters, String providerName) {
		ServiceAgent[] service = new ServiceAgent[names.length];
		for (int i = 0; i < names.length; i++) {
			SecureRandom sr = new SecureRandom();
			BigInteger bg = new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr);
			service[i] = new ServiceAgent();
			service[i].setIndexHash(bg.toString());
			service[i].setM(counters[i]);
			service[i].setName(names[i]);
			service[i].setProvider(getProviderByName(providerName));
		}
		return service;
	}

	///////////////////////////////////////////////////////////////////////
	/////////////////// PASS VERIFICATION///////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String verifyPass(String params) {
		System.out.println("Verify Pass");
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/ttp/private_TTP.der");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m1"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m1"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
//		JSONObject json = new JSONObject(params);
//		validateSignature(json.toString(),message.getString("Signature"));
		mCityPassService.initMCityPass(json.getLong("PASS"));
		serviceAgentService.initService(json.getLong("SERVICE"));
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
		activationService.initActivate(json.getLong("ACT"));
		schnorr = Schnorr.fromCertificate(json.getString("CERT"));
		schnorr.setA_3(new BigInteger(json.getString("A3")));
		if (!mCityPassService.verifyDatesPass() || counterService.getCounter().getCounter() == 0) {
			System.out.println("Ticket not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		json = new JSONObject();
		schnorr.sendChallenge();
		BigInteger Hu = new BigInteger(mCityPassService.getMCityPass().getHu());
		Hu_C = Hu.modPow(schnorr.getC(), schnorr.getP());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("c", schnorr.getC().toString());
		timestamp1 = System.currentTimeMillis();
		json.put("Timestamp", timestamp1);
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("Challenge", json.toString());
		message.put("Signature", signature);
		System.out.println(message.toString());
		return message.toString();
	}

	public String verifyPass2(String params) {
		System.out.println("Verify Pass 2");
		JSONObject json = new JSONObject(params);
		schnorr.setW3(new BigInteger(crypto.decryptWithPrivateKey(json.getString("w3"))));
		if (!schnorr.verifyChallenge(Hu_C)) {
			System.out.println("Verification failed");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}

		kandRI = getKandRI();
		BigInteger hk = new BigInteger(Cryptography.hash(kandRI[0]).getBytes());
		BigInteger RI = new BigInteger(kandRI[1]);
		Ap = RI.xor(schnorr.getPower(hk));

		json = new JSONObject();
		json.put("Ap", crypto.encryptWithPublicKey(Ap.toString()));
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp1);
		json.put("SERVICE", serviceAgentService.getServiceAgent().getIndexHash());
		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("Vsucc", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public String verifyProof(String params) {
		System.out.println("Verify Proof");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m3"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m3"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		BigInteger Au = new BigInteger(json.getString("Au"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(kandRI[0].getBytes()));
		System.out.println("PRNG: " + PRNG.toString());
		System.out.println("Au: " + Au.toString());
		String check = Au.xor(PRNG).toString();
		System.out.println("PSI:" + check);
		if (!Cryptography.hash(check).equals(counterService.getCounter().getPsi())) {
			System.out.println("CheckFalse");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}

		timestamp2 = System.currentTimeMillis();
		json = new JSONObject();
		json.put("Ap", Ap.toString());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp2);
		counterService.updateCounter();
			
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("R", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	///////////////////////////////////////////////////////////////////////
	/////////////// PASS Verification M-times//////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String verifyMPass(String params) {
		System.out.println("Verify Pass");
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/ttp/private_TTP.der");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m1"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m1"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		mCityPassService.initMCityPass(json.getLong("PASS"));
		serviceAgentService.initService(json.getLong("SERVICE"));
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
		activationService.initActivate(json.getLong("ACT"));
		schnorr = Schnorr.fromCertificate(json.getString("CERT"));
		schnorr.setA_3(new BigInteger(json.getString("A3")));
		System.out.println("Provider A3:" + schnorr.getA_3());
		if (!mCityPassService.verifyDatesPass() || !(counterService.getCounter().getCounter() >= 1)) {
			System.out.println("Ticket not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}

		json = new JSONObject();
		schnorr.sendChallenge();
		BigInteger Hu = new BigInteger(mCityPassService.getMCityPass().getHu());
		Hu_C = Hu.modPow(schnorr.getC(), schnorr.getP());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("c", schnorr.getC().toString());
		System.out.println("Provider c:" + schnorr.getC().toString());
		timestamp1 = System.currentTimeMillis();
		json.put("Timestamp", timestamp1);
	
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("Challenge", json.toString());
		message.put("Signature", signature);
		System.out.println(message.toString());
		return message.toString();
	}

	public String verifyMPass2(String params) {
		System.out.println("Verify Pass 2");
		JSONObject json = new JSONObject(params);
		schnorr.setW3(new BigInteger(crypto.decryptWithPrivateKey(json.getString("w3"))));
		if (!schnorr.verifyChallenge(Hu_C)) {
			System.out.println("Verification failed");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}

		kandRI = getKandRI();
		BigInteger hk = new BigInteger(Cryptography.hash(kandRI[0]).getBytes());
		BigInteger RI = new BigInteger(kandRI[1]);
		Ap = RI.xor(schnorr.getPower(hk));

		json = new JSONObject();
		json.put("Ap", crypto.encryptWithPublicKey(Ap.toString()));
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp1);
		json.put("SERVICE", serviceAgentService.getServiceAgent().getIndexHash());
		if (counterService.getCounter().getLastHash().equals(Constants.NOTUSED)) {
			json.put("lastHASH", counterService.getCounter().getPsi());
		} else {
			json.put("lastHASH", counterService.getCounter().getLastHash());
		}

		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("Vsucc", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public String verifyMProof(String params) {
		System.out.println("Verify Proof");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m3"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m3"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		// BigInteger Au = new BigInteger(json.getString("Au"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(kandRI[0].getBytes()));

		// System.out.println("PRNG: " + PRNG.toString());
		System.out.println("Au: " + json.getString("Au"));
		// String value = Au.xor(PRNG);
		String check = getOriginal(json.getString("Au"), PRNG.toString());
		System.out.println("Check: " + check);
		// System.out.println("PSI:" + check);

		if (counterService.getCounter().getLastHash().equals(Constants.NOTUSED)) {
			if (!Cryptography.hash(check).equals(counterService.getCounter().getPsi())) {
				System.out.println("CheckFalse for first time");
				json = new JSONObject();
				json.put("PASS", -1);
				return json.toString();
			}
		} else {
			if (!Cryptography.hash(check).equals(counterService.getCounter().getLastHash())) {
				System.out.println("CheckFalse for other times");
				System.out.println(Cryptography.hash(check));
				System.out.println(counterService.getCounter().getLastHash());
				json = new JSONObject();
				json.put("PASS", -1);
				return json.toString();
			}
		}

		timestamp2 = System.currentTimeMillis();
		json = new JSONObject();
		json.put("Ap", Ap.toString());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp2);

		counterService.updateCounter(check);
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("R", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	private String getOriginal(String A, String PRNG) {
		String PRNG32 = Cryptography.hash(PRNG);
		// String PRNG32 = Cryptography.hash("Text 1");
		System.out.println("Privider PRNG: " + PRNG32);
		System.out.println("Privider Au: " + A);
		byte[] PRNGbytes = null;
		byte[] Abytes = null;

		PRNGbytes = Base64.getDecoder().decode(PRNG32.getBytes());
		Abytes = Base64.getDecoder().decode(A.getBytes());

		byte[] xor = new byte[PRNGbytes.length];
		for (int i = 0; i < PRNGbytes.length; i++) {
			xor[i] = (byte) (PRNGbytes[i] ^ Abytes[i]);
		}
		return new String(Base64.getEncoder().encode(xor)).replace("\n", "");
	}
	
	
	
	///////////////////////////////////////////////////////////////////////
	/////////////// PASS Verification inf-times////////////////////////////
	///////////////////////////////////////////////////////////////////////
	public String verifyInfinitePass(String params){
		System.out.println("Verify Pass");
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/ttp/private_TTP.der");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m1"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m1"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		mCityPassService.initMCityPass(json.getLong("PASS"));
		serviceAgentService.initService(json.getLong("SERVICE"));
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
		activationService.initActivate(json.getLong("ACT"));
		schnorr = Schnorr.fromCertificate(json.getString("CERT"));
		schnorr.setA_3(new BigInteger(json.getString("A3")));
		System.out.println("Provider A3:" + schnorr.getA_3());
		if (!mCityPassService.verifyDatesPass() || serviceAgentService.getServiceAgent().getM() != -1) {
			System.out.println("Ticket not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		json = new JSONObject();
		schnorr.sendChallenge();
		BigInteger Hu = new BigInteger(mCityPassService.getMCityPass().getHu());
		Hu_C = Hu.modPow(schnorr.getC(), schnorr.getP());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("c", schnorr.getC().toString());
		timestamp1 = System.currentTimeMillis();
		json.put("Timestamp", timestamp1);

		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("Challenge", json.toString());
		message.put("Signature", signature);
		System.out.println(message.toString());
		return message.toString();
	}
	
	public String verifyInfinitePass2(String params) {
		System.out.println("Verify Pass 2");
		JSONObject json = new JSONObject(params);
		schnorr.setW3(new BigInteger(crypto.decryptWithPrivateKey(json.getString("w3"))));
		if (!schnorr.verifyChallenge(Hu_C)) {
			System.out.println("Verification failed");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}


		kandRI = getKandRI();
		BigInteger hk = new BigInteger(Cryptography.hash(kandRI[0]).getBytes());
		BigInteger RI = new BigInteger(kandRI[1]);
		Ap = RI.xor(schnorr.getPower(hk));

		json = new JSONObject();
		json.put("Ap", crypto.encryptWithPublicKey(Ap.toString()));
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp1);
		json.put("SERVICE", serviceAgentService.getServiceAgent().getIndexHash());
		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("Vsucc", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}
	
	public String verifyInfiniteProof(String params) {
		System.out.println("Verify Proof");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("m3"));
		//System.out.println(message.getString("Signature"));
		if(!validateSignature(message.getString("m3"), message.getString("Signature"))){
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		BigInteger Au = new BigInteger(json.getString("Au"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(kandRI[0].getBytes()));
		System.out.println("PRNG: " + PRNG.toString());
		System.out.println("Au: " + Au.toString());
		String check = Au.xor(PRNG).toString();
		System.out.println("PSI:" + check);
		if (!Cryptography.hash(check).equals(counterService.getCounter().getPsi())) {
			System.out.println("CheckFalse");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}

		timestamp2 = System.currentTimeMillis();
		json = new JSONObject();
		json.put("Ap", Ap.toString());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("Timestamp", timestamp2);
		counterService.updateInfiniteCounter();
		
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("R", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}
	
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	
	private boolean validateSignature(String param, String signature){
		boolean verify = crypto.getValidation(param, signature);
		System.out.println("Validating signature: " + verify);
		
		return verify;
	}
	
	private boolean validateSignature(String param){
		JSONObject json = new JSONObject(param);
		String signature = json.getString("Signature");
		json.remove("Signature");
		boolean verify = crypto.getValidation(json.toString(), signature);
		System.out.println("Validating signature: " + verify);
		
		return verify;	
	}
	
	

	public String[] authenticateProvider(String[] params) {
		return null;
	}

	public String[] verifyPass(String[] params) {
		return null;
	}

	private String[] getKandRI() {
		// crypto.initPrivateKey("cert/ttp/private_TTP.der");
		rightOfUseService.initRightOfUseByCityPass(mCityPassService.getMCityPass());
		JSONObject json = new JSONObject(rightOfUseService.getRightOfUse().getK());
		String[] params = new String[2];
		params[0] = json.getString("K");
		params[1] = json.getString("RI");

		return params;
	}

}