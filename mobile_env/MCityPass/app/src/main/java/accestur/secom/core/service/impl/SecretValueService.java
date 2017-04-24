/**
 * 
 */
package  accestur.secom.core.service.impl;


import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.SecretValue;
import  accestur.secom.core.service.SecretValueServiceInterface;

/**
 * @author Sebastià
 *
 */

public class SecretValueService implements SecretValueServiceInterface {
	

	
	private SecretValue secretValue;


	public SecretValueService(){}

	public SecretValue getSecretValue() {
		return secretValue;
	}
	
	public void initSecretValue(MCityPass mCityPass, boolean user){
		secretValue = new SecretValue(mCityPass, null, "10359018969389117182580930680709215177656381701540500435823092829920799917448102992516012615555782378004476151961081364614511064084755528696563069107662780");
	}


	@Override
	public void saveSecretValue(SecretValue secretValue) {

	}

	public void initSecretValue(MCityPass mCityPass) {
		secretValue =  getByMCityPass(mCityPass);
		
	}

	@Override
	public SecretValue getByMCityPass(MCityPass mCityPass) {
		return null;
	}


}
