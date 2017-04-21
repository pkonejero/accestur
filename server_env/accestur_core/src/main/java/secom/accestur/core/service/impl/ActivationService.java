package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.ActivationRepository;
import secom.accestur.core.model.Activation;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.ActivationServiceInterface;

@Service("activationService")
public class ActivationService implements ActivationServiceInterface{	
	
	@Autowired
	@Qualifier("activationRepository")
	private ActivationRepository activationRepository;
	
	private Activation activation;
	
	public ActivationService(){}
	
	public void initActivation(MCityPass mCityPass){
		activation = new Activation();
		activation.setId(new Long(1));
		activation.setActDate("21/04/2017");
		activation.setmCityPass(mCityPass);
		activation.setSignature("AJ7LHX4teXOXod6LqAQ6XyiLciyHdzzFMh3T/wyBWTbZpnnjDVgMRIP+Hzb6VVWBYISHewjOHQdS1x6TlLTpRLXs7sdKEJCKvpNOsCBg99WncgIJWvyTtN+mnuopo8QfEhfLAoekAaBufyRdcLYlfjppbHhV15etZesEJuWbzriEJTiFbNVHgqUSZkFVkJ9cp1KLHJ5neguR1Sn6pw3V/lqNX49SVAOS2rLZQBCP9rtW6i7wtSY9Q2G43UH8vEuyAtlqIhHJBeqviWXbjOdMBwvm0oAyqkdMpcIsYgRLj0R25jD73mpmMtgvdwWJbGkuwdZM/3OgHR4PvvWJJXJSiw==");
		activation.setState("Activated");
	}

	public Activation getActivationByMCityPassSn(MCityPass mCityPass){
		return activationRepository.findByMCityPass(mCityPass);
	}
	
	public Activation getActivation(){
		return activation;
	}

	public Activation getActivationById(long sn) {
		return activationRepository.findById(sn);
	}

	public void activateCityPass(Activation activation) {
		activationRepository.save(activation);
	}
	
	public void activateCityPass() {}

	public void initActivate(Long sn) {
		activation = getActivationById(sn);
	}

	
	
}