package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Service;

public interface ServiceInterface{
	
	public List<Service> getServicesByProvider();
	
	public List<Service> getServicesByMCityPass();
	
}