package accestur.secom.core.service.impl;



import  accestur.secom.core.model.Activation;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.service.ActivationServiceInterface;


public class ActivationService implements ActivationServiceInterface{

	private Activation activation;

	public ActivationService(){}

	public void initActivation(MCityPass mCityPass){
		activation = new Activation();
		//activation.setId(new Long(1));
		activation.setActDate("24/04/2017");
		activation.setMCityPass(mCityPass);
		activation.setSignature("WGzrT80EEZgv46HpN34d1jB8MTv4LKS/ftBefvXNUrPvBm0hhV4QMA1jbsu0CeE48WVJiurK8yzFrpBUHlqVblLqQzcmCaj4TLQUp7IegakI1oRNrCvVtxX6KFr23b9j3/qfXeAH3zsD95fwP98oG1lFvBHnbL4fIJqFhR/HNBwr1vQoOiVryeC43uinaREBDpYoxDTkVNcejF7IJrjZroZJqGKZC1Q2c7xR04hN5Iae5Hm4wT9wgDrBFz9S/BLDAw46ye2n7DEpCdOMPYDWHa+FHXlQuAJ2kdNEBcHjv0ctlqst+Rkht4iacVyGIp0JcVvj8z5raiVPX1mK9jtBmg==");
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
