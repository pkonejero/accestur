package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.ActivationFacadeInterface;
import secom.accestur.core.service.impl.ActivationService;

@Component("activationFacade")
public class ActivationFacade implements ActivationFacadeInterface {
	@Autowired
	@Qualifier("activationService")
	private ActivationService activationService;
	
	public ActivationService getActivationService(){
		return this.activationService;
	}
}