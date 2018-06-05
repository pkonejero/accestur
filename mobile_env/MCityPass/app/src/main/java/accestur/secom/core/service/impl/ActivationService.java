package accestur.secom.core.service.impl;



import com.activeandroid.query.Select;

import  accestur.secom.core.model.Activation;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.service.ActivationServiceInterface;


public class ActivationService implements ActivationServiceInterface{

	private Activation activation;

	public ActivationService(){}

	public void initActivation(MCityPass mCityPass){
		activation = new Select().from(Activation.class).where("mCityPass = ?", mCityPass.getId()).executeSingle();
		//activation.setId(new Long(1));

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
