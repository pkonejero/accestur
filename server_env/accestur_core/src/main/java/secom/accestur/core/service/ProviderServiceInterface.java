package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;

public interface ProviderServiceInterface{
	public void newProvider (String name, Issuer issuer);
		
	public Provider getProviderByName(String name);
	
	public List<Provider> getProvidersByIssuer(Issuer issuer);
	
	public ServiceAgent[] authenticateProvider(String[] serviceName, int[] counters, String providerName);
	
	public String verifyPass(String params);
	
	public String verifyPass2(String params);
	
	public String verifyProof(String params);
	
	public String verifyMPass(String params);
	
	public String verifyMPass2(String params);
	
	public String verifyMProof(String params);
	
	public String verifyInfiniteProof(String params);
	
	public String verifyInfinitePass2(String params);
	
	public String verifyInfinitePass(String params);
	
	public void createCertificate();
}