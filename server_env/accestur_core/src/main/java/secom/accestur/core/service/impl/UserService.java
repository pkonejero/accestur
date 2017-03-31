package secom.accestur.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
	@Qualifier("elGamal")
	private Elgamal elGamal;

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
		Elgamal ttpGamal = new Elgamal(Elgamal.readPublicCertificate("TTPPublicCertificate"));
		String hY = ttpGamal.Elgamal_PtToString(ttpGamal.decrypt(new Elgamal_CipherText(params[1])));
		String hashY = "";
		try {
			BigInteger y = new BigInteger(params[0]);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			hashY = new String(md.digest(y.toString().getBytes()), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hY.equals(hashY);
	}

	public void createCertificate() {
		// TODO Auto-generated method stub
		// elGamal.
		elGamal.createPrivateCertificate("UserPrivateCertificate");
		elGamal.createPublicCertificate("UserPublicCertificate");
	}

	public String[] authenticateUser() {
		// TODO Auto-generated method stub
		schnorr.Init();
		elGamal = new Elgamal(Elgamal.readPrivateCertificate("UserPrivateCertificate"));
		Elgamal ttpGamal = new Elgamal(Elgamal.readPublicCertificate("TTPPublicCertificate"));
		String params[] = new String[2];
		// Send Schnorr p
		// params[0] schnorr.getP().toString();
		// Send Schnorr q
		// params[1] = schnorr.getQ().toString();
		// Send Schnorr alpha
		// params[2] = schnorr.getG().toString();
		// Calulate Y
		schnorr.SecretKey();
		BigInteger y = schnorr.getG().modPow(schnorr.PublicKey(), schnorr.getP());
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String hY = new String(md.digest(y.toString().getBytes()), "UTF-8");
			Elgamal_CipherText ct = elGamal.encrypt(y.toString());
			params[0] = ct.toString();
			ct = ttpGamal.encrypt(hY);
			params[1] = ct.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
}