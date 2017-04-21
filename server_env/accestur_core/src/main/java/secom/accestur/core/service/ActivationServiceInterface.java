package secom.accestur.core.service;

import secom.accestur.core.model.Activation;
import secom.accestur.core.model.MCityPass;

public interface ActivationServiceInterface{
	public Activation getActivationByMCityPassSn(MCityPass mCityPass);
	
	public Activation getActivationById(long sn);
	
	public void activateCityPass(Activation activation);
	
	public void initActivate(Long sn);
	
}