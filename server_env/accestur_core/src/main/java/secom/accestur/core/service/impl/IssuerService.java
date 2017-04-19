package secom.accestur.core.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.model.Activation;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.IssuerServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("issuerService")
public class IssuerService implements IssuerServiceInterface{	
	@Autowired
	@Qualifier("issuerRepository")
	private IssuerRepository issuerRepository;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("mCityPassService")
	private MCityPassService mCityPassService;

	@Autowired
	@Qualifier("serviceAgentService")
	ServiceAgentService serviceAgentService;
	
	
	@Autowired
	@Qualifier("activationService")
	private ActivationService activationService;

	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

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
		String[] params = new String[10];
		params[0] = json.getString("user");
		params[1] = json.getString("certificate");
		params[2] = json.getString("hRU");
		params[3] = json.getString("Hu");
		params[4] = json.getString("A1");
		params[5] = json.getString("A2");
		params[6] = json.getString("Lifetime");
		params[7] = json.getString("Category");
		params[8] = json.getString("EXPDATE");
		params[9] = json.getString("PURDATE");
		return params;
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
			Issuer issuer = new Issuer();
			issuer.setName(name);
			System.out.println("Name: = " + issuer.getName());
			issuerRepository.save(issuer);		
		} else {
			System.out.println("This issuer already exisits, it will be initialized to the existing values");
		}
	}

	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}


	public String getPASS(String params){
		getChallengeMessage(params);
		schnorr.setW1(new BigInteger(ws[0]));
		schnorr.setW2(new BigInteger(ws[1]));
		MCityPass mCityPass = new MCityPass();
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
			
			mCityPass.setUser(userService.getUserByPseudonym(paramsOfPass[0]));
			mCityPass.setCategory(paramsOfPass[7]);
			mCityPass.setLifeTime(paramsOfPass[6]);
			mCityPass.sethRI(hRI);
			mCityPass.sethRU(paramsOfPass[2]);
			mCityPass.setDelta(delta);
			mCityPass.setTermsAndConditions(Constants.TERMS_AND_CONDITIONS);
			mCityPass.setExpDate(paramsOfPass[8]);
			mCityPass.setPurDate(paramsOfPass[9]);
			List<Counter> counters = new ArrayList<Counter>();
			for(int i = 0; i< services.length; i++ ){
				counters.add(new Counter(0, mCityPass, serviceAgentService.getServiceByName(services[i]), psi[i]));
			}
			mCityPass.setCounters(counters);
			mCityPass.setSignature(crypto.getSignature(mCityPass.toString()));
			mCityPassService.saveMCityPass(mCityPass);
//			System.out.println(mCityPass.toString());
//			System.out.println("Signature:" + crypto.getSignature(mCityPass.toString()));
		}
	return mCityPass.toString();
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
	
	public String verifyTicket(String params){
		
		JSONObject json = new JSONObject(params);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date now = new Date();
		Activation activation = null;
		mCityPassService.initMCityPass(json.getLong("Sn"));
		if(mCityPassService.verifyMCityPass()){
			activation = new Activation(dateFormat.format(now), mCityPassService.getMCityPass(),"Activated");
			activation.setSignature(crypto.getSignature(activation.stringToSign()));
			activationService.activateCityPass(activation);
		}
		
		if(activation == null){
			return "An error has ocurred";
		} else {
			return activation.toString();
		}
		
	}

}