package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import secom.accestur.core.facade.ServiceFacadeInterface;
import secom.accestur.core.service.impl.ServiceService;

@Component("serviceFacade")
public class ServiceFacade implements ServiceFacadeInterface{
	@Autowired
	@Qualifier("serviceService")
	private ServiceService serviceService;
	
	public ServiceService getServiceService(){
		return this.serviceService;
	}
	
	public Service getService(){
		return null;
	}
}
