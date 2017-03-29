package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.ProviderFacadeInterface;
import secom.accestur.core.service.impl.ProviderService;

@Component("providerFacade")
public class ProviderFacade implements ProviderFacadeInterface{
	@Autowired
	@Qualifier("providerService")
	private ProviderService providerService;
	
}