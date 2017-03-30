package secom.accestur.core.facade.impl;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.facade.RightOfUseFacadeInterface;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.service.impl.RightOfUseService;

@Component("rightOfUseFacade")
public class RightOfUseFacade implements RightOfUseFacadeInterface{
	@Autowired
	@Qualifier("rightOfUseService")
	private RightOfUseService rightOfUseService;

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.RightOfUseFacadeInterface#getRightOfUseService()
	 */
	@Override
	public RightOfUseService getRightOfUseService() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.facade.RightOfUseFacadeInterface#getRightOfUse()
	 */
	@Override
	public RightOfUse getRightOfUse() {
		// TODO Auto-generated method stub
		return null;
	}
	
}