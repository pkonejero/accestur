package secom.accestur.core.facade;

import java.util.List;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.User;
import secom.accestur.core.service.impl.UserService;

public interface UserFacadeInterface extends FacadeInterface{	
	public UserService getUserService();
	
	public User getUser();
	
	public User createUser();
	
	//public List<MCityPass> getMCityPasses(); 
		
	public boolean pseudonymGenerator();
	
	public MCityPass passPurchase();
	
}