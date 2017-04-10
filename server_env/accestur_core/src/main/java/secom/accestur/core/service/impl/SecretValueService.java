/**
 * 
 */
package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.SecretValueRepository;
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


	public void saveSecretValue(SecretValue secretValue) {
		secretValueRepository.save(secretValue);
	}
	
}
