package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import secom.accestur.core.facade.FacadeInterface;
import secom.accestur.core.facade.IssuerFacadeInterface;
import secom.accestur.core.model.Activation;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.service.impl.CounterService;
import secom.accestur.core.service.impl.IssuerService;



@Component("issuerFacade")
public class IssuerFacade implements IssuerFacadeInterface{
	@Autowired
	@Qualifier("issuerService")
	private IssuerService issuerService;
	
	public IssuerService getIssuerService(){
		return this.issuerService;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.IssuerFacadeInterface#getIssuer()
	 */
	@Override
	public Issuer getIssuer() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.IssuerFacadeInterface#passPurchase()
	 */
	@Override
	public boolean passPurchase() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.IssuerFacadeInterface#activateMCityPass()
	 */
	@Override
	public Activation activateMCityPass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}