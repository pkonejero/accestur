package secom.accestur.core.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import org.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
public class ProviderService implements ProviderServiceInterface{
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
	
	
	
	BigInteger Hu_C ;
	String[] kandRI;
	long timestamp1;
	long timestamp2;
	BigInteger Ap;
		
	public Provider getProviderByName(String name){
		return providerRepository.findByNameIgnoreCase(name);
	}
	
	public List<Provider> getProvidersByIssuer(Issuer issuer){
		return providerRepository.findByIssuer(issuer);
	}
	
	public void createCertificate(){
		crypto.initPrivateKey("private_ISSUER.der");
		crypto.initPublicKey("public_ISSUER.der");		
	}
	
	///////////////////////////////////////////////////////////////////////
	///////////////////PROVIDER AFFILIATION////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	public void newProvider(String name, Issuer issuer){
		Provider p = getProviderByName(name);
		if(p == null){
			Provider provider = new Provider();
			provider.setName(name);
			provider.setIssuer(issuer);
			providerRepository.save(provider);
			System.out.println("Name: = " + provider.getName());
		} else {
			System.out.println("This provider already exisits, it will be initialized to the existing values");
		}
		
	}
	
	public ServiceAgent[] authenticateProvider(String[] names, int[] counters, String providerName){
		ServiceAgent[] service = new ServiceAgent[names.length];
		for (int i = 0; i< names.length; i++){
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
	///////////////////PASS VERIFICATION///////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String verifyPass(String params){
		JSONObject json = new JSONObject(params);
		mCityPassService.initMCityPass(json.getLong("PASS"));
		serviceAgentService.initService(json.getLong("SERVICE"));
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
		activationService.initActivate(json.getLong("ACT"));
		schnorr = Schnorr.fromCertificate(json.getString("CERT"));
		schnorr.setA_3(new BigInteger(json.getString("A3")));
		if(!mCityPassService.verifyDatesPass()){
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
		return json.toString();
	}
	
	public String verifyPass2(String params){
		crypto.initPublicKey("cert/user/public_USER.der");
		crypto.initPrivateKey("cert/ttp/private_TTP.der");
		JSONObject json = new JSONObject(params);
		schnorr.setW3(new BigInteger(json.getString("w3")));
		if(!schnorr.verifyChallenge(Hu_C)){
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
		json.put("Signature", crypto.getSignature(json.toString()));
//		BigInteger Ap = RI.xor(new BigInteger(random));
//		System.out.println(Ap.toString());
		return json.toString();
	}
	
	public String verifyProof(String params){
		JSONObject json = new JSONObject(params);
		BigInteger Au = new BigInteger(json.getString("Au"));
		BigInteger  PRNG = schnorr.getPower(new BigInteger(kandRI[0].getBytes()));
		System.out.println("PRNG: " + PRNG.toString());
		System.out.println("Au: "+ Au.toString()) ;
		String check = Au.xor(PRNG).toString();
		System.out.println("PSI:" + check);
		if(!Cryptography.hash(check).equals(counterService.getCounter().getPsi())){
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
		json.put("Signature", crypto.getSignature(json.toString()));
		counterService.updateCounter();
		return json.toString();
	}
	
	
	
	
	

	public String[] authenticateProvider(String[] params){
		return null;
	}

	public String[] verifyPass(String[] params){
		return null;
	}
	
	private String[] getKandRI(){
		//crypto.initPrivateKey("cert/ttp/private_TTP.der");
		rightOfUseService.initRightOfUseByCityPass(mCityPassService.getMCityPass());
		JSONObject json = new JSONObject(rightOfUseService.getRightOfUse().getK());
		String[] params = new String[2];
		params[0] = json.getString("K");
		params[1] = json.getString("RI");
		
		return params;
	}

	

}