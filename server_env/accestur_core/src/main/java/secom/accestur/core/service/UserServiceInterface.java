package secom.accestur.core.service;

import secom.accestur.core.model.User;

public interface UserServiceInterface{
	
	public String getUserByPseudonym1(String pseudonym);
	
	public User getUserByPseudonym(String pseudonym);
	
	public String authenticateUser();
	
	public String getService();
	
	public String showPass();
	
	public String showProof();
	
	public boolean getValidationConfirmation();
	
	public String solveChallenge();
	
	public String receivePass();
	
	public String sendPass();
	
	public boolean verifyPseudonym();
	
	public void createCertificate();
}