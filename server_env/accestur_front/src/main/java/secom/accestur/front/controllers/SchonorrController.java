package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.utils.Constants;

@Controller
public class SchonorrController {
	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_a;

	@Autowired
	@Qualifier("schnorr")
	Schnorr schnorr_b;
	
	
	@Autowired
	@Qualifier("cryptography")
	Cryptography crA;
	
	@Autowired
	@Qualifier("cryptography")
	Cryptography crB;
	
	@Autowired
	@Qualifier("issuerModel")
	Issuer issuer;

	@RequestMapping("/schnorr")
	public String welcome(Map<String, Object> model){
//		issuer.setName("KARAPNA");
//		System.out.println(issuer.getName());
//		 
//         schnorr_a.Init();
//         schnorr_a.SecretKey();
//         schnorr_a.PublicKey();
//         
//         schnorr_b = Schnorr.fromCertificate(schnorr_a.getCertificate());
//         //schnorr_b.setPublicValues(schnorr_a.getPublicValues());
//
//         // A -> B (W)
//         schnorr_b.setW(schnorr_a.send_a_to_b_request());
//         // B -> A (e)
//         schnorr_a.setE(schnorr_b.send_b_to_a_challenge());
//         // A -> B (s)
//         schnorr_b.setJ(schnorr_a.send_a_to_b_resolve());
//
//         System.out.println(schnorr_b.verify());
//         //System.out.println(schnorr_a.toJson());
		crA.initPrivateKey("privateUser.der");
		crB.initPublicKey("publicUser.der");
		
		System.out.println(crB.getValidation(Constants.LOREM, crA.getSignature(Constants.LOREM)));
		return "schnorr";
	}
}
