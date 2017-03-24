package secom.accestur.front.controllers;

import java.math.BigInteger;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.schnorr.Schnorr;

@Controller
public class SchonorrController {
	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_a;

	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_b;

	@RequestMapping("/schnorr")
	public String welcome(Map<String, Object> model){
		
		BigInteger[] values;
		
		// Schnorr Agent A
		schnorr_a.Init();
		schnorr_a.SecretKey();
		schnorr_a.PublicKey();

		// Schnorr Agent B
		schnorr_b.setPublicValues(schnorr_a.getPublicValues());
		
		
		
		
		schnorr_a.send_a_to_b_request();
		
		
		// Schnorr Agent B
		schnorr_b.send_b_to_a_challenge();
		
		// Schnorr Agent A
		schnorr_a.send_a_to_b_resolve();
		schnorr_a.verify();
		return "schnorr";
	}
}
