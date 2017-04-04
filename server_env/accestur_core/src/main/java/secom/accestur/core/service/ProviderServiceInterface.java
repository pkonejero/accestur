package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;

public interface ProviderServiceInterface{
	public void newProvider (String name, Issuer issuer);
	
	public void addServiceProvider(Provider provider, List<ServiceAgent> serviceAgent);
	
	public Provider getProviderByName(String name);
	
	public void initiateProviderByName(String name);
	
	public List<Provider> getProvidersByIssuer(Issuer issuer);
	
	public ServiceAgent[] authenticateProvider(String[] names, int[] counters);
	
	public String[] verifyPass(String[] params);
	
	public void createCertificate();
}