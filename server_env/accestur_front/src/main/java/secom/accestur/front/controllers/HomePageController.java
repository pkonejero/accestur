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
import secom.accestur.core.service.impl.TrustedThirdPartyService;
import secom.accestur.core.service.impl.UserService;

@Controller
public class HomePageController{

	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("trustedThirdPartyService")
	TrustedThirdPartyService trustedThirdPartyService;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		
		userService.createCertificate();
		trustedThirdPartyService.createCertificate();
		boolean pseudonym = userService.verifyPseudonym(trustedThirdPartyService.generatePseudonym(userService.authenticateUser()));
		System.out.println("" + pseudonym);
		model.put("elgamal", "" + pseudonym);
		return "provider";
	}
}