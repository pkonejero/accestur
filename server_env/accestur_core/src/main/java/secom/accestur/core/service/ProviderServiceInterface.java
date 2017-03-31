package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Provider;

public interface ProviderServiceInterface{
	
	public Provider getProviderByName();
	
	public List<Provider> getProvidersByIssuer(long sn);

	public String[] authenticateProvider(String[] params);
	
	public String[] verifyPass(String[] params);
	
}