package secom.accestur.core.service.impl;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.elgamal.Elgamal_CipherText;
import secom.accestur.core.model.TrustedThirdParty;
import secom.accestur.core.service.TrustedThirdPartyServiceInterface;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("trustedThirdPartyService")
public class TrustedThirdPartyService implements TrustedThirdPartyServiceInterface{
	
	
	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	
	public TrustedThirdParty getTrustedThirdPartyByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String[] generatePseudonym(String[] params) {
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

	public void createCertificate() {
		// TODO Auto-generated method stub
		//elGamal.
		crypto.initPrivateKey("privateUser.der");
		crypto.initPublicKey("publicUser.der");	
	}
}