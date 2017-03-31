package secom.accestur.core.service.impl;

import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.ProviderServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Service("providerService")
public class ProviderService implements ProviderServiceInterface{
	@Autowired
	@Qualifier("providerModel")
	private Provider provider;
	
	@Autowired
	private ProviderRepository providerRepository;


	public Provider getProviderByName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Provider> getProvidersByIssuer(long sn) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String authenticateProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String verifyPass() {
		// TODO Auto-generated method stub
		return null;
	}



	public String[] authenticateProvider(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String[] verifyPass(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}
	
}