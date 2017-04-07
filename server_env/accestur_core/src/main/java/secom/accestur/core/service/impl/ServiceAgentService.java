package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import secom.accestur.core.dao.ServiceAgentRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.ServiceAgentInterface;

@Service("serviceAgentService")
public class ServiceAgentService implements ServiceAgentInterface{
	@Autowired
	@Qualifier("serviceAgentRepository")
	private ServiceAgentRepository serviceAgentRepository;

	public ServiceAgent getServiceByName(String name){
		return  serviceAgentRepository.findByNameIgnoreCase(name);
	}
	
	public void storeServices(ServiceAgent[] services){
		for (int i = 0; i < services.length; i++){
			if(serviceAgentRepository.findByNameIgnoreCase(services[i].getName())==null){
				serviceAgentRepository.save(services[i]);
			} else {
				System.out.println("This service already exists");
			}
		}
	}

	public List<ServiceAgent> getServicesByProvider(Provider provider){
		return null;
	}

	public List<ServiceAgent> getServicesByMCityPass(MCityPass mCityPass){
		return null;
	}
}