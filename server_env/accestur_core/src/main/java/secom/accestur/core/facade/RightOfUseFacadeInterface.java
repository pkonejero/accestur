package secom.accestur.core.facade;

import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.service.impl.RightOfUseService;

public interface RightOfUseFacadeInterface extends FacadeInterface {
	public RightOfUseService getRightOfUseService();
	
	public RightOfUse getRightOfUse();
}