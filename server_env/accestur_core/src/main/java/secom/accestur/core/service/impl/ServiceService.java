package secom.accestur.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.ServiceRepository;
import secom.accestur.core.service.ServiceInterface;

@Service("serviceService")
public class ServiceService implements ServiceInterface{
	@Autowired
	private ServiceRepository serviceRepository;

	public List<secom.accestur.core.model.Service> getServicesByProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<secom.accestur.core.model.Service> getServicesByMCityPass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void storeServices(secom.accestur.core.model.Service[] services){
		for (int i = 0; i < services.length; i++){
			serviceRepository.save(services[i]);
		}
	}
	
}


