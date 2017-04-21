package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;

public interface ServiceAgentInterface{
	
	public void initService(long id);
	
	public void initService(long id, boolean user);
	
	public void initService(String service);
	
	public ServiceAgent getServiceByName(String name);
	
	public ServiceAgent getServiceBySn(long id);
	
	public List<ServiceAgent> getServicesByProvider(Provider provider);
	
	public List<ServiceAgent> getServicesByMCityPass(MCityPass mCityPass);
}