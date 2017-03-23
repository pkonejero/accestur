package secom.accestur.front.controllers;

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
	Schnorr schnorr;

	@RequestMapping("/schnorr")
	public String welcome(Map<String, Object> model){
		schnorr.Init();
		model.put("rsa_gen",schnorr.Generator());
		model.put("rsa_secret_key",schnorr.SecretKey());
		model.put("rsa_public_key",schnorr.PublicKey());
		model.put("rsa_a_to_b",schnorr.send_a_to_b_request());
		model.put("rsa_b_to_a",schnorr.send_b_to_a_challenge());
		model.put("rsa_resolve",schnorr.send_a_to_b_resolve());
		model.put("rsa_verify",schnorr.verify());
		return "schnorr";
	}
}
