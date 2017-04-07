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
	Schnorr schnorr_a;

	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_b;

	@RequestMapping("/schnorr")
	public String welcome(Map<String, Object> model){
		schnorr_a.Init();
		schnorr_a.SecretKey();
		schnorr_a.PublicKey();
		schnorr_b.setW(schnorr_a.send_a_to_b_request());
		schnorr_a.setE(schnorr_b.send_b_to_a_challenge());
		schnorr_b.setJ(schnorr_a.send_a_to_b_resolve());
		System.out.println(schnorr_b.verify());
		return "schnorr";
	}
}