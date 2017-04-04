package secom.accestur.core.facade;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.impl.MCityPassService;

public interface MCityPassFacadeInterface extends FacadeInterface{
	public MCityPassService getMCityPassService();
	
	public MCityPass getMCityPass();
	
	public boolean activateMCityPass();
}