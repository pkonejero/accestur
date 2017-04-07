package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigInteger;
import org.json.*;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.IssuerServiceInterface;

@Service("issuerService")
public class IssuerService implements IssuerServiceInterface{
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

	private Issuer issuer;
	
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

	public String getChallenge(String[] params){
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

	public String[] getPASS(String params){
		getChallengeMessage(params);
		schnorr.setW1(new BigInteger(ws[0]));
		schnorr.setW2(new BigInteger(ws[1]));
		
		schnorr.verifyPASSQuery(yU_c, Hu_c);
		
		
		
		return null;
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