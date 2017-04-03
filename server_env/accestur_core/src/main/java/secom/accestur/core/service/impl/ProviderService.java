package secom.accestur.core.service.impl;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.ProviderServiceInterface;
import secom.accestur.core.utils.Constants;

import org.springframework.stereotype.Service;



import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Service("providerService")
public class ProviderService implements ProviderServiceInterface{
	@Autowired
	@Qualifier("providerModel")
	private Provider provider;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	@Autowired
	private ProviderRepository providerRepository;


	public Provider getProviderByName(String name) {
		// TODO Auto-generated method stub
		return providerRepository.findByNameIgnoreCase(name);
	}

	
	public void initiateProviderByName(String name) {
		// TODO Auto-generated method stub
		provider = getProviderByName(name);
	}
	
	public List<Provider> getProvidersByIssuer(String string) {
		// TODO Auto-generated method stub
		//for(int i =0; i < )
		return providerRepository.findByIssuerIgnoreCase(string);
	}


	
	public String verifyPass() {
		// TODO Auto-generated method stub
		return null;
	}



	public String[] authenticateProvider(String[] params) {
		
		
		
		return null;
	}

	
	public String[] verifyPass(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public void createCertificate() {
		crypto.initPrivateKey("privateUser.der");
		crypto.initPublicKey("publicUser.der");	
	}


	public void newProvider(String name, Issuer issuer) {
		provider.setName(name);
		provider.setIssuer(issuer);
		providerRepository.save(provider);
	}


	public secom.accestur.core.model.Service[] authenticateProvider(String[] names, int[] counters) {
		secom.accestur.core.model.Service[] service = new secom.accestur.core.model.Service[names.length];
		for (int i = 0; i< names.length; i++){
			SecureRandom sr = new SecureRandom();
			BigInteger bg = new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr);
			service[i] = new secom.accestur.core.model.Service();
			service[i].setIndexHash(bg.toString());
			service[i].setM(counters[i]);
			service[i].setName(names[i]);
			service[i].setProvider(provider);
		}
				
		return service;
	}



	
	
}