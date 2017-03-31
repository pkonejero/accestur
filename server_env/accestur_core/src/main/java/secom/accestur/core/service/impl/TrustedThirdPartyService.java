package secom.accestur.core.service.impl;

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
	@Qualifier("elGamal")
	private Elgamal elGamal;

	
	public TrustedThirdParty getTrustedThirdPartyByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String[] generatePseudonym(String[] params) {
		elGamal = new Elgamal(Elgamal.readPrivateCertificate("TTPPrivateCertificate"));
		Elgamal userGamal = new Elgamal(Elgamal.readPublicCertificate("UserPublicCertificate"));
		String[] message = new String[2];
		Elgamal_CipherText ct = new Elgamal_CipherText(params[1]);
		BigInteger y = new BigInteger(elGamal.Elgamal_PtToString(elGamal.decrypt(ct)));
		
		ct = new Elgamal_CipherText(params[0]);
		int hY = new Integer( userGamal.Elgamal_PtToString(userGamal.decrypt(ct)));
		if(hY != y.hashCode()){
			System.out.println("Y and hY do not match");
		} else {
			message[0] = y.toString();
			message[1] = elGamal.encrypt(""+ hY).toString();
		}
		
		return message;
	}

	public void createCertificate() {
		// TODO Auto-generated method stub
		//elGamal.
		elGamal.createPrivateCertificate("TTPPrivateCertificate");
		elGamal.createPublicCertificate("TTPPublicCertificate");		
	}
}