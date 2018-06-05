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

	public RightOfUseService getRightOfUseService(){
		return null;
	}
	
	public RightOfUse getRightOfUse(){
		return null;
	}
}