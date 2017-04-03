package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;

public interface ProviderServiceInterface{
	
	public Provider getProviderByName(String name);
	
	public void initiateProviderByName(String name);
	
	public List<Provider> getProvidersByIssuer(String string);
	
	public void newProvider (String name, Issuer issuer);

	public secom.accestur.core.model.Service[] authenticateProvider(String[] names, int[] counters);
	
	public String[] verifyPass(String[] params);
	
	public void createCertificate();
	
}