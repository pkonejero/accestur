package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		elgamal.createPrivateCertificate();
		elgamal.createPublicCertificate();
		
		elgamal.modifyPublicKey(Elgamal.readPublicCertificate());
		elgamal.modifyKset(Elgamal.readPrivateCertificate());
		
		model.put("elgamal",elgamal.Elgamal_PtToString(elgamal.));
		
		return "welcome";
	}
}