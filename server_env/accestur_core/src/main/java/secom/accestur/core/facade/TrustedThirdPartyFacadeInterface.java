package secom.accestur.core.facade;

import secom.accestur.core.model.TrustedThirdParty;
import secom.accestur.core.service.impl.TrustedThirdPartyService;

public interface TrustedThirdPartyFacadeInterface extends FacadeInterface{
	
	public TrustedThirdPartyService getTrustedThirdPartyService();
	
	public TrustedThirdParty getTrustedThirdParty();
	
	public boolean generatePseudonym();
}