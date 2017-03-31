package secom.accestur.core.service;

public interface IssuerServiceInterface{
	public String getIssuerByName(String name);
	
	public String generateCertificate();
	
	public String[] getChallenge(String [] params);
	
	public String[] getPASS(String[] params);
	
	public String[] verifyTicket(String[] params);
	
	public boolean arrayGeneration();
}