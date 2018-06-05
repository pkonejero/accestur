package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import secom.accestur.core.facade.IssuerFacadeInterface;
import secom.accestur.core.model.Activation;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.service.impl.IssuerService;

@Component("issuerFacade")
public class IssuerFacade implements IssuerFacadeInterface{
	@Autowired
	@Qualifier("issuerService")
	private IssuerService issuerService;
	
	public IssuerService getIssuerService(){
		return this.issuerService;
	}

	public Issuer getIssuer(){
		return null;
	}

	public boolean passPurchase(){
		return false;
	}

	public Activation activateMCityPass(){
		return null;
	}
}