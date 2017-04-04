package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import secom.accestur.core.dao.ServiceAgentRepository;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.ServiceAgentInterface;

@Service("serviceAgentService")
public class ServiceAgentService implements ServiceAgentInterface{
	@Autowired
	@Qualifier("serviceAgentRepository")
	private ServiceAgentRepository serviceAgentRepository;

	public List<ServiceAgent> getServicesByProvider(){
		return null;
	}

	public List<ServiceAgent> getServicesByMCityPass(){
		return null;
	}
	
	public void storeServices(ServiceAgent[] services){
		for (int i = 0; i < services.length; i++){
			serviceAgentRepository.save(services[i]);
		}
	}
}