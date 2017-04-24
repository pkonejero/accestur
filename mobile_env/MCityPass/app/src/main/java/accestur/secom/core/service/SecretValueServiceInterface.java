/**
 * 
 */
package accestur.secom.core.service;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.SecretValue;

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
