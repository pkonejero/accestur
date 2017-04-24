package accestur.secom.core.service.impl;



import  accestur.secom.core.model.Activation;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.service.ActivationServiceInterface;


public class ActivationService implements ActivationServiceInterface{
	
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
	
	public Activation getActivation(){
		return activation;
	}

	@Override
	public Activation getActivationByMCityPassSn(MCityPass mCityPass) {
		return null;
	}

	@Override
	public Activation getActivationById(long sn) {
		return null;
	}

	@Override
	public void activateCityPass(Activation activation) {

	}

	public void activateCityPass() {}

	public void initActivate(Long sn) {
		activation = getActivationById(sn);
	}

	
	
}