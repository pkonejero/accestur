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

	public Activation getActivationByMCityPassSn(MCityPass mCityPass){
		return activationRepository.findByMCityPass(mCityPass);
	}

	public Activation getActivationById(long sn) {
		return activationRepository.findById(sn);
	}

	public void activateCityPass(Activation activation) {
		activationRepository.save(activation);
	}



	public void initActivate(Long sn) {
		activation = getActivationById(sn);
	}




	
	
}