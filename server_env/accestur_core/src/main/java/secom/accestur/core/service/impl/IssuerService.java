package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigInteger;
import java.util.Random;

import org.json.*;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.IssuerServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("issuerService")
public class IssuerService implements IssuerServiceInterface{
	@Autowired
	@Qualifier("issuerModel")
	private Issuer issuer;

	@Autowired
	@Qualifier("issuerRepository")
	private IssuerRepository issuerRepository;
	
	@Autowired
	@Qualifier("serviceAgentService")
	ServiceAgentService serviceAgentService;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	@Autowired
	@Qualifier("mcitypassModel")
	MCityPass mCityPass;
	
	
	
	//Values necessary to create CiyPass;
	private String[] paramsOfPass;
	private BigInteger yU_c;
	private BigInteger Hu_c;
	
	private String[] ws;
	private String[] services;
	private String[] psi;

	public Issuer getIssuerByName(String name){
		return issuerRepository.findByNameIgnoreCase(name);
	}

	public String generateCertificate(ServiceAgent[] services){
		serviceAgentService.storeServices(services);
		arrayGeneration();
		return "Services Generated";
	}

	public String getChallenge(String json){
		String[] params = solveChallengeMessage(json);
		paramsOfPass = params;
		schnorr = Schnorr.fromCertificate(params[1]);
		schnorr.setA_1(new BigInteger(params[4]));
		schnorr.setA_2(new BigInteger(params[5]));
		BigInteger yU = new BigInteger(getYu(params[0]));
		BigInteger Hu = new BigInteger(params[3]);
		BigInteger c = schnorr.getRandom();
		schnorr.setC(c);
		System.out.println("Issuer sends:");
		System.out.println("c: " +c.toString());
		yU_c = yU.modPow(c, schnorr.getP());
		Hu_c = Hu.modPow(c, schnorr.getP());
		return c.toString();
	}
	
	private String[] solveChallengeMessage (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[8];
		params[0] = json.getString("user");
		params[1] = json.getString("certificate");
		params[2] = json.getString("hRU");
		params[3] = json.getString("Hu");
		params[4] = json.getString("A1");
		params[5] = json.getString("A2");
		params[6] = json.getString("Lifetime");
		params[7] = json.getString("Category");
		
		return params;
 	}
	
	private void getChallengeMessage(String params){
		//System.out.println(params);
		JSONObject json = new JSONObject(params);
		//System.out.println(json.toString());
		ws = new String[2];
		//System.out.println(json.getString("w1"));
		ws[0] = crypto.decryptWithPrivateKey(json.getString("w1"));
		ws[1] = crypto.decryptWithPrivateKey(json.getString("w2"));
//		System.out.println("Issuer gets:");
//		System.out.println("w1: " +ws[0]);
//		System.out.println("w2: " +ws[1]);
		JSONArray jsonArray = json.getJSONArray("services");
		psi = new String[jsonArray.length()];
		services = new String[jsonArray.length()];
		JSONObject jsonObject;
		String s;
		for(int i = 0; i < psi.length; i++){
			s = jsonArray.getString(i);
			jsonObject = new JSONObject(crypto.decryptWithPrivateKey(s));
			psi[i] = jsonObject.getString("psi");
			services[i] = jsonObject.getString("service");
		}
		
	}
	
	public String getPASS(String params){
		getChallengeMessage(params);
		schnorr.setW1(new BigInteger(ws[0]));
		schnorr.setW2(new BigInteger(ws[1]));
		
		if(!schnorr.verifyPASSQuery(yU_c, Hu_c)){
			System.out.println("Authentication failed");
			return 	"Authentication failed";		
		} else {
			BigInteger RI = new BigInteger(Constants.PRIME_BITS, new Random());
			String hRI = Cryptography.hash(RI.toString());
			//Generate k
			crypto.initPublicKey("cert/ttp/public_TTP.der");
			JSONObject json = new JSONObject();
			json.put("K", Cryptography.hash((new BigInteger(ws[1])).toString()));
			json.put("RI", RI);
			String k = crypto.getSignature(json.toString());
			JSONObject rou = new JSONObject();
			rou.put("k", crypto.encryptWithPublicKey(json.toString()));
			rou.put("signature" , k);
			String delta = rou.toString();
			RightOfUse rightOfUse = new RightOfUse(json.toString(), k);
			
			mCityPass.setCategory(paramsOfPass[7]);
			mCityPass.setLifeTime(paramsOfPass[6]);
			mCityPass.sethRI(hRI);
			mCityPass.sethRU(paramsOfPass[2]);
			
			
			List<Counter> counters;
			
			
		}
		
		
		
		return null;
	}

	public String[] verifyTicket(String[] params){
		return null;
	}

	public boolean arrayGeneration(){
		return false;
	}

	public void createCertificate(){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		crypto.initPublicKey("cert/user/public_USER.der");	
	}

	public void newIssuer(String name){
		Issuer i = getIssuerByName(name);
		if(i==null){
			issuer.setName(name);
			System.out.println("Name: = " + issuer.getName());
			issuerRepository.save(issuer);		
		} else {
			System.out.println("This issuer already exisits, it will be initialized to the existing values");
			issuer = i;
			System.out.println("Name: = " + issuer.getName());
		}
		
	}
	
	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}	
}