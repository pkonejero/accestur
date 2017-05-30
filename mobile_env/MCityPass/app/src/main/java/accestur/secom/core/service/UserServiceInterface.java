package accestur.secom.core.service;

import accestur.secom.core.model.User;

public interface UserServiceInterface{
	public String getUserByPseudonym1(String pseudonym);
	
	public User getUserByPseudonym(String pseudonym);
	
	public void initUser();

	public String authenticateUser();
	
	public boolean verifyPseudonym(String[] params);
	
	public String getService();
	
	public String showTicket(long CityPassId, long serviceId);
	
	public String showPass(long sn);
	
	public void getVerifyTicketConfirmation(String s);
	
	public String solveVerifyChallenge(String params);
	
	public String[] showProof(String[] params);
	
	public boolean getValidationConfirmation(String params);
	
	public String solveChallenge(String c, String[] services);
	
	public String receivePass(String params);
	
	public String sendPass();
	
	public void createCertificate();
	
	public String showMTicket(long CityPassId, long serviceId);
	
	public String getVerifyMTicketConfirmation(String params);
	
	public String showMProof(String params);
	
	public String solveMVerifyChallenge(String params);
	
	public boolean getInfiniteValidationConfirmation(String params);
	
	public String showInfiniteProof(String params);
	
	public String solveInfiniteVerifyChallenge(String params);
	
	public String showInfinitePass(long CityPassId, long serviceId);
	
	
}