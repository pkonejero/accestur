/**
 * 
 */
package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.SecretValueRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.SecretValue;
import secom.accestur.core.service.SecretValueServiceInterface;

/**
 * @author Sebastià
 *
 */
@Service("secretvalueService")
public class SecretValueService implements SecretValueServiceInterface {
	
	@Autowired
	@Qualifier("secretvalueRepository")
	private SecretValueRepository secretValueRepository;
	
	private SecretValue secretValue;


	public SecretValueService(){}
	
	public void saveSecretValue(SecretValue secretValue) {
		secretValueRepository.save(secretValue);
	}

	

	public SecretValue getSecretValue() {
		return secretValue;
	}
	
	public void initSecretValue(MCityPass mCityPass, boolean user){
		secretValue = new SecretValue(mCityPass, null, "10359018969389117182580930680709215177656381701540500435823092829920799917448102992516012615555782378004476151961081364614511064084755528696563069107662780");
	}



	public void initSecretValue(MCityPass mCityPass) {
		secretValue =  getByMCityPass(mCityPass);
		
	}


	public SecretValue getByMCityPass(MCityPass mCityPass) {
	
		return secretValueRepository.findByMCityPass(mCityPass);
	}
	
}
