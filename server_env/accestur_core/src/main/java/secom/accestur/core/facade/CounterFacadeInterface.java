/**
 * 
 */
package secom.accestur.core.facade;

import secom.accestur.core.model.Counter;
import secom.accestur.core.service.impl.CounterService;

/**
 * @author Sebastià
 *
 */
public interface CounterFacadeInterface extends FacadeInterface {

	public CounterService getCounterService();
	
	public Counter getCounter();
	
}
