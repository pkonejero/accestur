package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import secom.accestur.core.facade.ServiceAgentFacadeInterface;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.impl.ServiceAgentService;

@Component("serviceFacade")
public class ServiceAgentFacade implements ServiceAgentFacadeInterface{
	@Autowired
	@Qualifier("serviceAgentService")
	private ServiceAgentService serviceAgentService;

	public ServiceAgentService getServiceAgentService(){
		return this.serviceAgentService;
	}
	public ServiceAgent getServiceAgent() {
		return null;
	}
}