package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.ProviderServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("providerService")
public class ProviderService implements ProviderServiceInterface{
	@Autowired
	@Qualifier("providerModel")
	private Provider provider;

	@Autowired
	@Qualifier("providerRepository")
	private ProviderRepository providerRepository;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	public void newProvider(String name, Issuer issuer){
		provider.setName(name);
		provider.setIssuer(issuer);
		providerRepository.save(provider);
	}
		
	public Provider getProviderByName(String name){
		return providerRepository.findByNameIgnoreCase(name);
	}

	public void initiateProviderByName(String name){
		provider = getProviderByName(name);
	}
	
	public List<Provider> getProvidersByIssuer(Issuer issuer){
		return providerRepository.findByIssuer(issuer);
	}

	public String verifyPass(){
		return null;
	}

	public String[] authenticateProvider(String[] params){
		return null;
	}

	public String[] verifyPass(String[] params){
		return null;
	}

	public void createCertificate(){
		crypto.initPrivateKey("privateUser.der");
		crypto.initPublicKey("publicUser.der");	
	}
	
	public ServiceAgent[] authenticateProvider(String[] names, int[] counters){
		ServiceAgent[] service = new ServiceAgent[names.length];
		for (int i = 0; i< names.length; i++){
			SecureRandom sr = new SecureRandom();
			BigInteger bg = new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr);
			service[i] = new ServiceAgent();
			service[i].setIndexHash(bg.toString());
			service[i].setM(counters[i]);
			service[i].setName(names[i]);
			service[i].setProvider(provider);
		}	
		return service;
	}	
}