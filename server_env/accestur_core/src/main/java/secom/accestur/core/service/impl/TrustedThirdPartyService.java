package secom.accestur.core.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.dao.TrustedThirdPartyRepository;
import secom.accestur.core.model.TrustedThirdParty;
import secom.accestur.core.model.User;
import secom.accestur.core.service.TrustedThirdPartyServiceInterface;

@Service("trustedThirdPartyService")
public class TrustedThirdPartyService implements TrustedThirdPartyServiceInterface{
	@Autowired
	@Qualifier("ttpRepository")
	TrustedThirdPartyRepository trustedThirdPartyRepository;
	
	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	

	public TrustedThirdParty getTrustedThirdPartyByName(String name){
		return null;
	}

	public String generatePseudonym(String input){
		createCertificate();
		JSONObject json = new JSONObject(input);
		String[] params = new String[2];
		params[0] = json.getString("signature");
		params[1] = json.getString("y");
		
		String[] message = new String[2];
		String yU = crypto.decryptWithPrivateKey(params[1]);
		if (crypto.getValidation(yU, params[0])){
			message[0] = yU;
			message[1] = crypto.getSignature(yU);
			userService.saveUser(message[0], message[1]);
		} else {
			message[0] = "Error";
		}
		json = new JSONObject();
		json.put("y", message[0]);
		json.put("signature", message[1]);
		json.put("Sn", userService.getUser().getId());
		
		return json.toString();
	}

	public void createCertificate(){
		crypto.initPrivateKey("cert/ttp/private_TTP.der");
		crypto.initPublicKey("cert/user/public_USER.der");		
	}
}