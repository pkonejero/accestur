package secom.accestur.core.service.impl;

import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.ProviderServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Service("providerService")
public class ProviderService implements ProviderServiceInterface{
	@Autowired
	@Qualifier("providerModel")
	private Provider provider;
	
	@Autowired
	private ProviderRepository providerRepository;
	
}