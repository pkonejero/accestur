package secom.accestur.core.service;

import secom.accestur.core.model.User;

public interface UserServiceInterface{
	public String getUserByPseudonym1(String pseudonym);
	
	public User getUserByPseudonym(String pseudonym);

	public String[] authenticateUser();
	
	public boolean verifyPseudonym(String[] params);
	
	public String getService();
	
	public String[] showTicket();
	
	public String showPass(long sn);
	
	public void getVerifyTicketConfirmation(String s);
	
	public String[] showProof(String[] params);
	
	public boolean getValidationConfirmation();
	
	public String solveChallenge(String c, String[] services);
	
	public String receivePass(String params);
	
	public String sendPass();
	
	public void createCertificate();
}