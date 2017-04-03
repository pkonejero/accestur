package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.service.IssuerServiceInterface;

@Service("issuerService")
public class IssuerService implements IssuerServiceInterface{
	@Autowired
	@Qualifier("serviceService")
	ServiceService serviceService;
	
	@Autowired
	@Qualifier("issuerModel")
	private Issuer issuer;
	
	
	@Autowired
	private IssuerRepository issuerRepository;
	
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

	
	public Issuer getIssuerByName(String name) {
		// TODO Auto-generated method stub
		return issuerRepository.findByNameIgnoreCase(name);
	}


	public String generateCertificate(secom.accestur.core.model.Service[] services) {
		serviceService.storeServices(services);
		arrayGeneration();
		return "Services Generated";
	}


	public String getChallenge(String[] params) {
		paramsOfPass = params;
		schnorr = Schnorr.fromCertificate(params[1]);
		BigInteger yU = new BigInteger(getYu(params[0]));
		BigInteger Hu = new BigInteger(params[3]);
		BigInteger c = schnorr.send_b_to_a_challenge();
		yU_c = yU.modPow(c, schnorr.getP());
		Hu_c = yU.modPow(c, schnorr.getP());
		return c.toString();
	}


	public String[] getPASS(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] verifyTicket(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean arrayGeneration() {
		// TODO Auto-generated method stub
		return false;
	}


	public void createCertificate() {
		// TODO Auto-generated method stub
		
	}

	public void newIssuer(String name) {
		issuer.setName(name);
		issuerRepository.save(issuer);
	}
	
	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString(y);
	}


	


	
	
}