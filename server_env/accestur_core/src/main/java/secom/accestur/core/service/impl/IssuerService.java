package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.service.IssuerServiceInterface;

@Service("issuerService")
public class IssuerService implements IssuerServiceInterface{
	@Autowired
	@Qualifier("issuerModel")
	private Issuer issuer;
	
	@Autowired
	private IssuerRepository issuerRepository;

	
	public String getIssuerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	public String generateCertificate() {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] getChallenge(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] getPASS(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	public String[] verifyTicket(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean arrayGeneration() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}