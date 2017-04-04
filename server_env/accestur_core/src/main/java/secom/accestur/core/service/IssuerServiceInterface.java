package secom.accestur.core.service;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.ServiceAgent;

public interface IssuerServiceInterface{
	public Issuer getIssuerByName(String name);

	public String generateCertificate(ServiceAgent[] services);

	public void newIssuer(String name);

	public String getChallenge(String [] params);

	public String[] getPASS(String[] params);

	public String[] verifyTicket(String[] params);

	public boolean arrayGeneration();

	public void createCertificate();
}