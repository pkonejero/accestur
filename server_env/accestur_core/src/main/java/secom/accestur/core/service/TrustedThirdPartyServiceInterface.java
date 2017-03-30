package secom.accestur.core.service;

import secom.accestur.core.model.TrustedThirdParty;

public interface TrustedThirdPartyServiceInterface{
	
	public TrustedThirdParty getTrustedThirdPartyByName(String name);
	
	public String generatePseudonym();
	
	
	
}