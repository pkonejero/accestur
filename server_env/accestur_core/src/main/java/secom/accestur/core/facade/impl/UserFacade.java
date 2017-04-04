package secom.accestur.core.facade.impl;

import java.util.List;

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

	public User createUser(){
		return null;
	}

	public boolean pseudonymGenerator(){
		return false;
	}
	
	public MCityPass passPurchase(){
		return null;
	}
	
	public List<MCityPass> getMCityPasses(){
		return null;
	}
}