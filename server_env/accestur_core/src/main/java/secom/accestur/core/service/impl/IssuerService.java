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
	
	//Values necessary to create CiyPass;
	private String[] paramsOfPass;
	private BigInteger yU_c;
	private BigInteger Hu_c;

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
		BigInteger yU = new BigInteger(getYu(params[0]));
		BigInteger Hu = new BigInteger(params[3]);
		BigInteger c = schnorr.send_b_to_a_challenge();
		yU_c = yU.modPow(c, schnorr.getP());
		Hu_c = yU.modPow(c, schnorr.getP());
		return c.toString();
	}

	public String[] getPASS(String[] params){
		return null;
	}

	public String[] verifyTicket(String[] params){
		return null;
	}

	public boolean arrayGeneration(){
		return false;
	}

	public void createCertificate(){		
	}

	public void newIssuer(String name){
		issuer.setName(name);
		issuerRepository.save(issuer);
	}
	
	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}	
}