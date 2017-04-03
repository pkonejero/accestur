package secom.accestur.core.service;

import secom.accestur.core.model.Issuer;

public interface IssuerServiceInterface{
	public Issuer getIssuerByName(String name);
	
	public String generateCertificate(secom.accestur.core.model.Service[] services);
	
	public void newIssuer(String name);
	
	public String getChallenge(String [] params);
	
	public String[] getPASS(String[] params);
	
	public String[] verifyTicket(String[] params);
	
	public boolean arrayGeneration();
	
	public void createCertificate();
}