/**
 * 
 */
package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import secom.accestur.core.facade.ServiceFacadeInterface;
import secom.accestur.core.service.impl.ServiceService;

/**
 * @author Sebastià
 *
 */

@Component("serviceFacade")
public class ServiceFacade implements ServiceFacadeInterface {
	@Autowired
	@Qualifier("serviceService")
	private ServiceService serviceService;
	
	public ServiceService getServiceService(){
		return this.serviceService;
	}
}
