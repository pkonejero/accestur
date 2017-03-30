package secom.accestur.core.service;

public interface IssuerServiceInterface{
	public String getIssuerByName(String name);
	
	public String generateCertificate();
	
	public boolean arrayGeneration();
	
	public String verifyTicket();
}