package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.MCityPassFacadeInterface;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.impl.MCityPassService;

@Component("mCityPassFacade")
public class MCityPassFacade implements MCityPassFacadeInterface{
	@Autowired
	@Qualifier("mCityPassService")
	private MCityPassService mCityPassService;
	
	public MCityPassService getMCityPassService(){
		return this.mCityPassService;
	}
	
	public MCityPass getMCityPass(){
		return null;
	}

	public boolean activateMCityPass(){
		return false;
	}
}