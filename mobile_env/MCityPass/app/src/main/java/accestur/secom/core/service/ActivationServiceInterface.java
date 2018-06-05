package accestur.secom.core.service;

import accestur.secom.core.model.Activation;
import accestur.secom.core.model.MCityPass;

public interface ActivationServiceInterface{
	public Activation getActivationByMCityPassSn(MCityPass mCityPass);
	
	public Activation getActivationById(long sn);
	
	public void activateCityPass(Activation activation);
	
	public void activateCityPass();
	
	public void initActivate(Long sn);
	
}