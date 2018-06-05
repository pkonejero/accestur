package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.TrustedThirdPartyFacadeInterface;
import secom.accestur.core.model.TrustedThirdParty;
import secom.accestur.core.service.impl.TrustedThirdPartyService;

@Component("trustedThirdPartyFacade")
public class TrustedThirdPartyFacade implements TrustedThirdPartyFacadeInterface{
	@Autowired
	@Qualifier("trustedThirdPartyService")
	private TrustedThirdPartyService trustedThirdPartyService;
	
	public TrustedThirdPartyService getTrustedThirdPartyService(){
		return this.trustedThirdPartyService;
	}
	
	public TrustedThirdParty getTrustedThirdParty(){
		return null;
	}

	public boolean generatePseudonym(){
		return false;
	}	
}