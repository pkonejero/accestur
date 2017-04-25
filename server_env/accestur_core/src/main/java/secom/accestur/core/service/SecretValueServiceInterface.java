/**
 * 
 */
package secom.accestur.core.service;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.SecretValue;

/**
 * @author Sebastià
 *
 */
public interface SecretValueServiceInterface {
	
	public void saveSecretValue(SecretValue secretValue);
	
	public void initSecretValue(MCityPass mCityPass);
	
	public SecretValue getByMCityPass(MCityPass mCityPass);

	public SecretValue getSecretValue();
}
