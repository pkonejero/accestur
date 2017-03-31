package secom.accestur.core.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import secom.accestur.core.facade.UserFacadeInterface;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.User;
import secom.accestur.core.service.impl.UserService;

@Component("userFacade")
public class UserFacade implements UserFacadeInterface{
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	public UserService getUserService(){
		return this.userService;
	}

	public User getUser(){
		return this.userService.getUser();
	}

	
	public User createUser() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean pseudonymGenerator() {
		// TODO Auto-generated method stub
		return false;
	}


	public MCityPass passPurchase() {
		// TODO Auto-generated method stub
		return null;
	}
}