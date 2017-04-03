package secom.accestur.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import org.json.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.elgamal.Elgamal_CipherText;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.UserRepository;
import secom.accestur.core.model.User;
import secom.accestur.core.service.UserServiceInterface;

@Service("userService")
public class UserService implements UserServiceInterface {
	@Autowired
	@Qualifier("userModel")
	private User user;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	public String getUserByPseudonym1(String pseudonym) {
		// Pick the first element - If you comment this line a new element will
		// be created
		user = userRepository.findAll().iterator().next();
		if (user != null) {
			// Modify the first element
			user.setPseudonym(pseudonym);
			// Save the firts element
			userRepository.save(user);
		}

		return pseudonym;
	}

	public User getUser() {
		return userRepository.findAll().iterator().next();
	}

	public User getUserByPseudonym(String pseudonym) {
		// TODO Auto-generated method stub
		return null;
	}

	public String showProof() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getValidationConfirmation() {
		// TODO Auto-generated method stub
		return false;
	}

	public String solveChallenge() {
		// TODO Auto-generated method stub
		return null;
	}

	public String receivePass() {
		// TODO Auto-generated method stub
		return null;
	}

	public String sendPass() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean verifyPseudonym(String[] params) {
		// TODO Auto-generated method stub		
		boolean verified = crypto.getValidation(params[0], params[1]);
		if(verified) {
			user.setPseudonym(params[0]);
			user.setSignature(params[1]);
			user.setSchnorrParameters(schnorr.getParameters());
			System.out.println(user.getPseudonym());
			//System.out.println(user.getSchnorrCertificate());
			userRepository.save(user);
		}
		return verified;
	}

	public void createCertificate() {
		// TODO Auto-generated method stub
		schnorr.Init();
		schnorr.SecretKey();
		schnorr.PublicKey();
		crypto.initPrivateKey("privateUser.der");
		crypto.initPublicKey("publicUser.der");
	}

	public String[] authenticateUser() {
		String params[] = new String[3];
		BigInteger y = schnorr.getY();
		params[0] = crypto.getSignature(y.toString());
		params[1] = crypto.encryptWithPublicKey(y.toString());
		
		
		return params;
	}

	public String[] getService() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] showTicket() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] showPass() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] showProof(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] solveChallenge(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] receivePass(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String generatePseudonym(String y, String signature){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("y", y);
		jsonObject.put("signature", signature);
		return jsonObject.toString();
	}
}