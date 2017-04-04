package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.ServiceAgent;

public interface ServiceAgentInterface{
	public List<ServiceAgent> getServicesByProvider();
	
	public List<ServiceAgent> getServicesByMCityPass();
}