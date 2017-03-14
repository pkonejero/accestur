package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.rsa.RSA;

@Controller
public class HomePageController{
	@Autowired
	@Qualifier("elGamal")
	Elgamal elgamal;
		
	@Autowired
	@Qualifier("rsa")
	RSA rsa;
	
	@Value("${welcome.message}")
	private String message;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		model.put("message",this.message);
		model.put("elgamal",elgamal.Elgamal_PtToString(elgamal.decrypt(elgamal.encrypt(message))));
		model.put("rsa",rsa.RSA_PtToString(rsa.decrypt(rsa.encrypt(message))));
		return "welcome";
	}
}