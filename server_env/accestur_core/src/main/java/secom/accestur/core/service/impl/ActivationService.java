package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.ActivationRepository;
import secom.accestur.core.model.Activation;
import secom.accestur.core.service.ActivationServiceInterface;

@Service("activationService")
public class ActivationService implements ActivationServiceInterface{
	@Autowired
	@Qualifier("activationModel")
	private Activation activation;
	
	@Autowired
	@Qualifier("activationRepository")
	private ActivationRepository activationRepository;

	public Activation getActivationByMCityPassSn(long sn){
		return null;
	}
}