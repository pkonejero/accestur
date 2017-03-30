package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.UserRepository;
import secom.accestur.core.model.User;
import secom.accestur.core.service.UserServiceInterface;

@Service("userService")
public class UserService implements UserServiceInterface{
	@Autowired
	@Qualifier("userModel")
	private User user;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr; 
	
	@Autowired
	@Qualifier("elGammal")
	private Elgamal elGammal;
	
	public String getUserByPseudonym1(String pseudonym){
		// Pick the first element - If you comment this line a new element will be created
		user = userRepository.findAll().iterator().next();
		if(user != null)
		{
			// Modify the first element
			user.setPseudonym(pseudonym);
			// Save the firts element
			userRepository.save(user);
		}

		return pseudonym;
	}
	
	public User getUser(){
		return userRepository.findAll().iterator().next();
	}


	
	public User getUserByPseudonym(String pseudonym) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String authenticateUser() {
		// TODO Auto-generated method stub
		schnorr.Init();
		BigInteger yU = schnorr.send_a_to_b_request(); 
		return null;
	}

	
	public String getService() {
		// TODO Auto-generated method stub
		return null;
	}


	public String showPass() {
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


	public boolean verifyPseudonym() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void createCertificate() {
		// TODO Auto-generated method stub
		//elGamal.
		
	}
}