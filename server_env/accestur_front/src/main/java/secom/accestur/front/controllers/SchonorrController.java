package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.service.impl.IssuerService;
import secom.accestur.core.service.impl.UserService;

@Controller
public class SchonorrController {
	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_a;

	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_b;
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("issuerService")
	IssuerService issuerService;

	@RequestMapping("/schnorr_test")
	public String welcome(Map<String, Object> model){
		schnorr_a = Schnorr.fromPrivateCertificate(userService.getUser().getSchnorr());
		schnorr_b = Schnorr.fromCertificate(schnorr_a.getCertificate());
        schnorr_b.setW(schnorr_a.send_a_to_b_request());
        schnorr_a.setE(schnorr_b.send_b_to_a_challenge());
        schnorr_b.setJ(schnorr_a.send_a_to_b_resolve());

		return "schnorr";
	}
}