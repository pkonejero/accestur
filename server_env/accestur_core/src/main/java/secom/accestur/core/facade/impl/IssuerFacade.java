package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import secom.accestur.core.facade.FacadeInterface;
import secom.accestur.core.service.impl.CounterService;
import secom.accestur.core.service.impl.IssuerService;



@Component("issuerFacade")
public class IssuerFacade implements FacadeInterface{
	@Autowired
	@Qualifier("issuerService")
	private IssuerService issuerService;
	
	public IssuerService getIssuerService(){
		return this.issuerService;
	}
	
	
	
}