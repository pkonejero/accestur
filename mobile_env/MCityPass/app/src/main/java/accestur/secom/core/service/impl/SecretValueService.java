/**
 *
 */
package  accestur.secom.core.service.impl;


import com.activeandroid.query.Select;

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
		secretValue = new SecretValue(mCityPass, null, "4932534343769240949516927354727418182613549131672892081342954339745779720890276455324718761433422929096209367409545237057649653210629886981381190932657617");
	}


	@Override
	public void saveSecretValue(SecretValue secretValue) {
		secretValue.save();
	}

	public void initSecretValue(MCityPass mCityPass) {
		secretValue =  getByMCityPass(mCityPass);

	}

	@Override
	public SecretValue getByMCityPass(MCityPass mCityPass) {
		return new Select().from(SecretValue.class)
				.where("mCityPass = ? ", mCityPass.getId())
				.executeSingle();
	}


}
