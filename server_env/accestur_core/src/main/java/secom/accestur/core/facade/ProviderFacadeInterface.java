package secom.accestur.core.facade;

import secom.accestur.core.model.Provider;
import secom.accestur.core.service.impl.ProviderService;

public interface ProviderFacadeInterface extends FacadeInterface{
	public ProviderService getProviderService();
	
	public Provider getProvider();
	
	public boolean providerAffiliation();
	
	public boolean passVerification();
}