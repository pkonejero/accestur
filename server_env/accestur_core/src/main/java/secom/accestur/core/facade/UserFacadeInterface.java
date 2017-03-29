package secom.accestur.core.facade;

import secom.accestur.core.service.impl.UserService;

public interface UserFacadeInterface extends FacadeInterface{	
	public UserService getUserService();
}