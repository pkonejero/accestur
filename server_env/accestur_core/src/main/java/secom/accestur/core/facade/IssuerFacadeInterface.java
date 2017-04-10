package secom.accestur.core.facade;

import secom.accestur.core.model.Activation;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.service.impl.IssuerService;

public interface IssuerFacadeInterface extends FacadeInterface{
	public IssuerService getIssuerService();
	
	public Issuer getIssuer();
	
	public boolean passPurchase();
	
	public Activation activateMCityPass();
}