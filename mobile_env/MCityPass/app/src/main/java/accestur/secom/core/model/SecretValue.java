/**
 * 
 */
package accestur.secom.core.model;


/**
 * @author Sebastià
 *
 */

public class SecretValue extends DomainObjectModel {

	private Long id;

	private Provider provider;

	private MCityPass mCityPass;
	
	private String secret;
	
	public SecretValue(){}

	
	public SecretValue(MCityPass mCityPass, Provider provider, String secret){
		this.mCityPass = mCityPass;
		this.provider = provider;
		this.secret = secret;
	}


	public Provider getProvider() {
		return provider;
	}


	public void setProvider(Provider provider) {
		this.provider = provider;
	}


	public MCityPass getmCityPass() {
		return mCityPass;
	}


	public void setmCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
	

}
