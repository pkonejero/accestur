/**
 * 
 */
package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import secom.accestur.core.facade.CounterFacadeInterface;
import secom.accestur.core.service.impl.CounterService;


/**
 * @author Sebastià
 *
 */
@Component("counterFacade")
public class CounterFacade implements CounterFacadeInterface {
	@Autowired
	@Qualifier("counterService")
	private CounterService counterService;
	
	public CounterService getCounterService(){
		return this.counterService;
	}
	
}
