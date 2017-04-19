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
	
	public void createCertificate();
}