package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.ProviderFacadeInterface;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.impl.ProviderService;

@Component("providerFacade")
public class ProviderFacade implements ProviderFacadeInterface{
	@Autowired
	@Qualifier("providerService")
	private ProviderService providerService;

	public ProviderService getProviderService() {
		return providerService;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.ProviderFacadeInterface#getProvider()
	 */
	@Override
	public Provider getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.ProviderFacadeInterface#providerAffiliation()
	 */
	@Override
	public boolean providerAffiliation() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.ProviderFacadeInterface#passVerification()
	 */
	@Override
	public boolean passVerification() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}