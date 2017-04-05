package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.rsa.RSA;
import secom.accestur.core.facade.impl.UserFacade;
import secom.accestur.core.model.User;

@Controller
public class HomePageController{
	@Autowired
	@Qualifier("elGamal")
	Elgamal elgamal;

	@Autowired
	@Qualifier("rsa")
	RSA rsa;
	
	@Autowired
	@Qualifier("userFacade")
	UserFacade userFacade;
	
	@Autowired
	@Qualifier("userModel")
	User user;

	@RequestMapping("/home")
	public String welcome(Map<String, Object> model){
		// Example 
		userFacade.getUserService().getUserByPseudonym("OHHHHH");
		return "welcome";
	}
}