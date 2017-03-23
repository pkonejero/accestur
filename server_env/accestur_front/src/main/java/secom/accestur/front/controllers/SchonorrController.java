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
	
	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr2;

	@RequestMapping("/schnorr")
	public String welcome(Map<String, Object> model){
		System.out.println("Init");
		schnorr.Init();
		schnorr2.setPublicValues(schnorr.getPublicValues());
		schnorr2.SecureParam(schnorr.SecureParam());
		
		
		System.out.println("Secret Key");
		model.put("rsa_secret_key",schnorr.SecretKey());
		System.out.println("Public Key");
		model.put("rsa_public_key",schnorr.PublicKey());

		schnorr2.setY(schnorr.getY());
		schnorr2.get_a_to_b_request(schnorr.send_a_to_b_request());
		schnorr.get_b_to_a_challenge(schnorr.send_b_to_a_challenge());
		schnorr2.get_a_to_b_resolve(schnorr.send_a_to_b_resolve());
		System.out.println("Verify");
		model.put("rsa_verify",schnorr2.verify());
		return "schnorr";
	}
}
