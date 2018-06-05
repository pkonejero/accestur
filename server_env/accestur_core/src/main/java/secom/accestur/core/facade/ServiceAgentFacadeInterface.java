package secom.accestur.core.facade;

import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.impl.ServiceAgentService;

public interface ServiceAgentFacadeInterface extends FacadeInterface{
	public ServiceAgentService getServiceAgentService();
	
	public ServiceAgent getServiceAgent();
}
