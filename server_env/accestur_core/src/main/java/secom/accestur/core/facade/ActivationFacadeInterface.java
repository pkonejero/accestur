package secom.accestur.core.facade;

import secom.accestur.core.model.Activation;
import secom.accestur.core.service.impl.ActivationService;

public interface ActivationFacadeInterface extends FacadeInterface{
	public ActivationService getActivationService();
	
	public Activation getActivation();
}