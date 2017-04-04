package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.dao.TrustedThirdPartyRepository;
import secom.accestur.core.model.TrustedThirdParty;
import secom.accestur.core.service.TrustedThirdPartyServiceInterface;

@Service("trustedThirdPartyService")
public class TrustedThirdPartyService implements TrustedThirdPartyServiceInterface{
	@Autowired
	@Qualifier("ttpRepository")
	TrustedThirdPartyRepository trustedThirdPartyRepository;
	
	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	public TrustedThirdParty getTrustedThirdPartyByName(String name){
		return null;
	}

	public String[] generatePseudonym(String[] params){
		String[] message = new String[2];
		String yU = crypto.decryptWithPrivateKey(params[1]);
		if (crypto.getValidation(yU, params[0])){
			message[0] = yU;
			message[1] = crypto.getSignature(yU);
			
		} else {
			message[0] = "Error";
		}
		return message;
	}

	public void createCertificate(){
		crypto.initPrivateKey("privateUser.der");
		crypto.initPublicKey("publicUser.der");	
	}
}