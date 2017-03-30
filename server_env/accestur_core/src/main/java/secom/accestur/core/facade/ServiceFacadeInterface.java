/**
 * 
 */
package secom.accestur.core.facade;

import org.springframework.stereotype.Service;

import secom.accestur.core.service.impl.ServiceService;

/**
 * @author Sebastià
 *
 */
public interface ServiceFacadeInterface extends FacadeInterface {

	public ServiceService getServiceService();
	
	public Service getService();
}
