package accestur.secom.core.service;

import java.util.List;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.Provider;
import accestur.secom.core.model.ServiceAgent;

public interface ServiceAgentInterface{
	
	public void initService(long id);
	
	public void initService(long id, boolean user);
	
	public void initService(String service);
	
	public ServiceAgent getServiceByName(String name);
	
	public ServiceAgent getServiceBySn(long id);
	
	public List<ServiceAgent> getServicesByProvider(String provider);
	
	public List<ServiceAgent> getServicesByMCityPass(MCityPass mCityPass);
}